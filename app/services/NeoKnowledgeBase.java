package services;

import models.MedicalHistory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.server.rest.web.NodeNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.*;

/**
 * User: ecsark
 * Date: 12/22/14
 * Time: 01:12
 */
@Singleton
public class NeoKnowledgeBase {

    private GraphDatabaseService db;
    private NeoStructureManager stm;
    private Calendar calendar = Calendar.getInstance();

    @Inject
    public NeoKnowledgeBase(@Named("Neo4j URL") String url) {
        db = new RestGraphDatabase(url);
        stm = new NeoStructureManager(db);
    }


    public void newUser (long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cre_ts", currentTime());
        stm.newUser(userId, params);
    }

    public void commitSymptoms (long sessionId, List<Integer> symptomIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("cre_ts", currentTime());
        for (int symId : symptomIds) {
            try {
                stm.tellSymptom(sessionId, symId, params);
            } catch (NodeNotFoundException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public void commitDisease (long sessionId, int diseaseId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cre_ts", currentTime());
        try {
            stm.indicateDisease(sessionId, diseaseId, params);
        } catch (NodeNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    // DiseaseId -> score
    public Map<Integer, Double> evaluate (List<Integer> symptomIds) {
        Map<Integer, Double> diseaseScores = new HashMap<>();
        // TODO
        return diseaseScores;
    }


    public Map<Integer, Double> evaluate (List<Integer> symptomIds, long sessionId) {
        Map<Integer, Double> diseaseScores = new HashMap<>();
        // TODO
        return diseaseScores;
    }

    public void updateUserInfo (long userId, Map<String, Object> params) {
        try {
            stm.setUserInfo(userId, params);
        } catch (NodeNotFoundException e) {
            // TODO
            e.printStackTrace();
        }
    }

    public List<MedicalHistory> retrieveUserHistory (long userId) {
        List<MedicalHistory> histories = new ArrayList<>();
        try {
            Node userNode = stm.getUserOrException(userId);

        } catch (NodeNotFoundException e) {
            e.printStackTrace();
        }
        return histories;
    }


    // QuestionIds
    public List<Integer> nextQuestion (EvaluationContext context) {
        int topDiseaseId = getKeyofMaxValue(context.eval);
        Map<Integer, Double> questionScores = new HashMap<>();
        List<Integer> questions = new ArrayList<>();
        questions.add(getKeyofMaxValue(questionScores));
        // TODO
        return questions;
    }

    private <T> T getKeyofMaxValue (Map<T, Double> map) {
        T maxValueKey = null;
        double maxValue = Double.MIN_VALUE;
        for (Map.Entry<T, Double> entry : map.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxValueKey = entry.getKey();
            }
        }
        return maxValueKey;
    }

    private Timestamp currentTime () {
        return new Timestamp(calendar.getTime().getTime());
    }
}

class EvaluationContext {
    Map<Integer, Double> eval;



}