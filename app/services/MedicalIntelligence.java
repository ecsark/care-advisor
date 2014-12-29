package services;

import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import repositories.*;
import utils.EvaluationContext;

import java.util.List;

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
        return questionGroupRepo.getRelatedDiseases(questionGroupRepo.findOne(Integer.toUnsignedLong(429)));
    }

    public EvaluationContext<NDisease> evaluate (List<Long> questionGroupIds, List<Long> symptomIds) {
        EvaluationContext<Long> matched = new EvaluationContext<>();
        EvaluationContext<Long> unmatched = new EvaluationContext<>();
        EvaluationContext<Long> missed = new EvaluationContext<>();

        // TODO: NullPointerException
        for (long questionGroupId : questionGroupIds) {
            NQuestionGroup question = questionGroupRepo.findOne(questionGroupId);
            for (RQuestion q :question.questions) {
                NSymptom symptom = q.symptomChoice;
                for (RCause c : symptom.causedBy) {
                    if (symptomIds.contains(c.disease.id)) {

                    }


                }
            }
        }
        return null;
    }
}
