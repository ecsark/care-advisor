package services;

import messages.MDialogue;
import messages.MQuestion;
import messages.MedicalChoice;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.*;
import utils.EvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private QuestionGroupRepository questionGroupRepo;
    @Autowired
    private SymptomGroupRepository symptomGroupRepo;
    @Autowired
    private CheckupRepository checkupRepo;
    @Autowired
    private Neo4jTemplate template;

    public List<NDisease> relatedDiseases () {
        List<Long> questions = new ArrayList<>();
        questions.add((long)429); questions.add((long)430);
        return questionGroupRepo.getRelatedDiseases(questions);
    }

    public EvaluationContext<NDisease> evaluate (MDialogue answer) {

        EvaluationContext<NDisease> fScores = new EvaluationContext<>();

        EvaluationContext<NDisease> matched = new EvaluationContext<>();
        EvaluationContext<NDisease> unmatched = new EvaluationContext<>();
        EvaluationContext<NDisease> missed = new EvaluationContext<>();

        //TODO: security
        List<NDisease> relatedDiseases = questionGroupRepo.getRelatedDiseases(answer.getQuestionIds());

        for (NDisease disease : relatedDiseases) {
            Map<Long, RCause> symptomToCause = disease.mapBySymptomId();

            for (MQuestion ans : answer.questions) {
                boolean alreadyUnmatched = false;
                boolean alreadyMatched = false;
                boolean probablyMissed = true;
                for (MedicalChoice choice : ans.options) {
                    // TODO: reason more about cause
                    if (symptomToCause.containsKey(choice.answerId)) { // disease 1, user 1
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

    @Transactional
    public MDialogue furtherQuestions (EvaluationContext<NDisease> diseaseEvaluation, MDialogue answer) {


        List<NDisease> topDiseases = diseaseEvaluation.getKeyOfTopNValue(5); // TODO: reason about this threshold
        NDisease top1 = topDiseases.get(0);

        List<Long> askedSymptomIds = questionGroupRepo.getRelatedSymptoms(answer.getQuestionIds())
                .stream().map(s -> s.id)
                .collect(Collectors.toList());

        List<NSymptom> restSymptoms =  top1.causingSymptoms.stream()
                .map(c -> c.symptom)
                .filter(s -> !askedSymptomIds.contains(s.id))
                .collect(Collectors.toList());

        EvaluationContext<NSymptom> symEval = new EvaluationContext<>();
        for (NSymptom symptom : restSymptoms) {

            int hit = 0;

            for (RCause cause : template.fetch(symptom).causedBy) {
                if (topDiseases.contains(cause.disease)) {
                    hit += 1;
                }
            }

            double entropy = getBinaryEntropy(hit, topDiseases.size()-hit);
            symEval.put(symptom, entropy);
        }

        NSymptom niceQuestion = symEval.getKeyOfMaxValue();
        NQuestionGroup questionGroup = fetch(niceQuestion.question).questionGroup;

        MDialogue dialog = new MDialogue();

        MQuestion ask = dialog.createQuestion();
        ask.questionId = questionGroup.id;
        ask.questionType = questionGroup.qType;
        ask.questionText = questionGroup.cnText;
        for (RQuestion q : questionGroup.questions) {
            ask.createChoice()
                    .setAnswerId(q.symptomChoice.id)
                    .setAnswerText(q.cnText);
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
