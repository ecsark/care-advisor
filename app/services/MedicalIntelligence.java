package services;

import messages.*;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.*;
import utils.EvaluationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: ecsark
 * Date: 12/30/14
 * Time: 01:44
 */
@Service
public class MedicalIntelligence {
    @Autowired
    private SymptomRepository symptomRepo;
    @Autowired
    private DiseaseRepository diseaseRepo;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private SymptomGroupRepository symptomGroupRepo;
    @Autowired
    private CheckupRepository checkupRepo;
    @Autowired
    private Neo4jTemplate template;

    @Transactional
    public EvaluationContext<NDisease> evaluate (MQuery query) {

        EvaluationContext<NDisease> fScores = new EvaluationContext<>();

        EvaluationContext<NDisease> matched = new EvaluationContext<>();
        EvaluationContext<NDisease> unmatched = new EvaluationContext<>();
        EvaluationContext<NDisease> missed = new EvaluationContext<>();

        //TODO: sanity check
        List<NDisease> relatedDiseases = questionRepo.getRelatedDiseases(query.getQuestionIds());

        List<MItem> mainQuestions = query.mainAnswer.items;
        List<Long> includedSymptomGroupsIds = mainQuestions.stream()
                .filter(item -> item.answerValue != null && item.answerValue > 0.8)
                .map(item -> item.id).collect(Collectors.toList());
        List<Long> excludedSymptomGroupsIds = mainQuestions.stream()
                .filter(item -> item.answerValue != null && item.answerValue < 0.2)
                .map(item -> item.id).collect(Collectors.toList());

        //TODO: include in fscore computation
        //List<Long> excludedSymptomIds = symptomGroupRepo.getRelatedSymptomIds(excludedSymptomGroupsIds);

        List<NDisease> groupIncludedDiseases = symptomGroupRepo.getRelatedDiseases(includedSymptomGroupsIds);

        // TODO: probably merge them
        if (relatedDiseases.size() == 0)
            relatedDiseases = groupIncludedDiseases;


        for (NDisease disease : relatedDiseases) {
            Map<Long, RCause> symptomToCause = disease.mapBySymptomId();

            for (MAnswer ans : query.answers) {
                boolean alreadyUnmatched = false;
                boolean alreadyMatched = false;
                boolean probablyMissed = true;
                for (MItem item : ans.items) {
                    // TODO: reason more about cause
                    if (symptomToCause.containsKey(item.id)) { // disease 1, user 1
                        probablyMissed = false;
                        if (!alreadyMatched) {
                            matched.plus(disease);
                            alreadyMatched = true;
                        }
                    } else if (!alreadyUnmatched) { // disease 0, user 1
                        unmatched.plus(disease);
                        alreadyUnmatched = true;
                    }
                }
                if (!probablyMissed)
                    missed.plus(disease); // disease 1, user 0
            }

            double fs = getFScore(matched.getOrZero(disease), unmatched.getOrZero(disease), missed.getOrZero(disease));
            fScores.put(disease, fs);
        }

        return fScores;
    }

    public MReply firstQuestion () {

        MReply dialog = new MReply();
        MQuestion ask = dialog.createQuestion();
        ask.questionId = NSymptomGroup.getQuestionId();
        ask.questionType = NQuestion.SINGLE_CHOICE;
        symptomGroupRepo.findAll().forEach(sgroup -> {
            MItem item = ask.createItem();
            item.id = sgroup.id;
            item.questionText = sgroup.cnText;
        });

        return dialog;

    }

    @Transactional
    public MAdvice getAdvice (EvaluationContext<NDisease> diseaseEvaluation) {
        List<NDisease> topDiseases = diseaseEvaluation.getKeyOfTopNValue(3); // TODO: customize this
        EvaluationContext<NCheckup> checkupEval = new EvaluationContext<>();

        for (NDisease dis : topDiseases) {
            for (RDepend dep :dis.dependentCheckups) {
                template.fetch(dep);
                checkupEval.plus(dep.checkup, dep.importance * diseaseEvaluation.eval.get(dis));
            }
        }

        MAdvice advice = new MAdvice();

        final List<Map.Entry<NCheckup, Double>> recommended = checkupEval.getEntryOfTopNValue(5);// TODO: customize this
        for (Map.Entry<NCheckup, Double> r : recommended) {
            MCheckup newCheckup = advice.addCheckup();
            NCheckup ck = template.fetch(r.getKey());
            newCheckup.id = ck.id;
            newCheckup.importance = r.getValue();
            newCheckup.name = ck.cnText;
        }

        return advice;

    }


    @Transactional
    public MReply furtherQuestions (EvaluationContext<NDisease> diseaseEvaluation, MQuery query) {

        if (diseaseEvaluation.eval.isEmpty())
            return firstQuestion();

        List<MItem> mainQuestions = query.mainAnswer.items;
        Set<Long> includedSymptomGroupsIds = mainQuestions.stream()
                .filter(item -> item.answerValue != null && item.answerValue > 0.8)
                .map(item -> item.id).collect(Collectors.toSet());
        Set<Long> excludedSymptomGroupsIds = mainQuestions.stream()
                .filter(item -> item.answerValue != null && item.answerValue < 0.2)
                .map(item -> item.id).collect(Collectors.toSet());



        List<NDisease> topDiseases = diseaseEvaluation.getKeyOfTopNValue(10); // TODO: reason about this threshold
        NDisease top1 = topDiseases.get(0);

        List<Long> askedSymptomIds = questionRepo.getRelatedSymptoms(query.getQuestionIds())
                .stream().map(s -> s.id)
                .collect(Collectors.toList());

        List<NSymptom> restSymptoms =  top1.causingSymptoms.stream()
                .map(c -> c.symptom)
                .filter(s -> !askedSymptomIds.contains(s.id))
                .collect(Collectors.toList());

        EvaluationContext<NSymptom> symEval = new EvaluationContext<>();
        for (NSymptom symptom : restSymptoms) {
            // if symptom group is excluded
            template.fetch(symptom);
            if (symptom.symptomGroup != null &&
                    excludedSymptomGroupsIds.contains(symptom.symptomGroup.id))
                continue;

            int hit = 0;

            for (RCause cause : template.fetch(symptom).causedBy) {
                if (topDiseases.contains(cause.disease)) {
                    hit += 1;
                }
            }

            double entropy = getBinaryEntropy(hit, topDiseases.size()-hit);
            symEval.put(symptom, entropy);
        }


        MReply dialog = new MReply();
        MQuestion ask = dialog.createQuestion();

        NSymptom niceSymptom = symEval.getKeyOfMaxValue();
        NSymptomGroup sgroup = niceSymptom.symptomGroup;
        // SymptomGroup asked
        if (sgroup != null
                && !includedSymptomGroupsIds.contains(sgroup.id)) {
            template.fetch(sgroup);
            ask.questionId = NSymptomGroup.getQuestionId(); //
            ask.questionType = NQuestion.BINARY;
            ask.questionText = sgroup.getQuestion();
            MItem item = ask.createItem();
            item.id = sgroup.id;
            item.questionText = sgroup.cnText;

        } else {
            NQuestion questionGroup = fetch(niceSymptom.question).question;
            ask.questionId = questionGroup.id;
            ask.questionType = questionGroup.qType;
            ask.questionText = questionGroup.cnText;
            for (RAsk q : questionGroup.questions) {
                MItem item = ask.createItem();
                item.id = q.symptomChoice.id;
                item.questionText = q.cnText;
            }
        }
        return dialog;
    }


    private static double getBinaryEntropy (int size1, int size2) {
        if (size1 == 0 || size2 == 0)
            return 0.0;
        int sum = size1 + size2;
        double share1 = (double) size1 / sum;
        double share2 = 1.0 - share1;
        return - (share1*Math.log(share1) + share2*Math.log(share2));
    }

    private static double getEntropy (List<Integer> classSize) {
        int sum = 0;
        for (int s : classSize)
            sum += s;
        double entropy = 0.0;
        for (int s : classSize) {
            double share = (double) s / sum;
            entropy += share * Math.log(share);
        }
        return -entropy;
    }

    private static double getFScore (double matched, double unmatched, double missed) {
        if (matched == 0)
            return 0;
        double precision = matched / ( matched + unmatched );
        double recall = matched / ( matched + missed );
        return 2 * precision * recall / ( precision + recall );
    }

    private <T> T fetch (T obj ) {
        return template.fetch(obj);
    }
}
