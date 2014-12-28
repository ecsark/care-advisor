package services;

import models.NodeLabel;
import models.RelationLabel;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Map;

/**
 * User: ecsark
 * Date: 12/22/14
 * Time: 22:51
 */
@Singleton
@Service
public class NeoStructureManager {

    //@Inject
    private GraphDatabaseService db;

    //@Inject
    //private Neo4jTemplate template;

    @Inject
    private UserRepository userRepository;

    private Index<Node> userIndex;
    private Index<Node> sessionIndex;
    private Index<Node> symptomIndex;
    private Index<Node> diseaseIndex;

    private final String USER_ID_KEY = "user_id";
    private final String SESSION_ID_KEY = "session_id";
    private final String SYMPTOM_ID_KEY = "symptom_id";
    private final String DISEASE_ID_KEY = "disease_id";

    /*public NeoStructureManager() {
        userIndex = db.index().forNodes("user_nodes");
        sessionIndex = db.index().forNodes("session_nodes");
        symptomIndex = db.index().forNodes("symptom_nodes");
        diseaseIndex = db.index().forNodes("disease_nodes");
    }*/

    protected void newSession (long userId, long sessionId, Map<String, Object> sessionParams, Map<String, Object> relationParams) {

        Node userNode = null;
        try {
            userNode = getUserOrException(userId);
        } catch (NodeNotFoundException e) {
            userNode = newUser(userId, Collections.EMPTY_MAP);
        }

        try (Transaction tx = db.beginTx()) {
            Node sessionNode = db.createNode(NodeLabel.TOKEN);
            setProperties(sessionNode, sessionParams);
            sessionIndex.add(sessionNode, SESSION_ID_KEY, sessionId);

            Relationship user_session = userNode.createRelationshipTo(sessionNode, RelationLabel.EXPERIENCES);
            setProperties(user_session, relationParams);

            tx.success();
        }
    }

    protected void tellSymptom(long sessionId, int symptomId, Map<String, Object> relationParams) throws NodeNotFoundException {

        Node sessionNode = getSessionOrException(sessionId);

        Node symptomNode = getSymptomOrException(symptomId);

        try (Transaction tx = db.beginTx()) {

            Relationship session_symptom = sessionNode.createRelationshipTo(symptomNode, RelationLabel.SHOWS);
            setProperties(session_symptom, relationParams);
            tx.success();
        }
    }


    protected void inferDisease (long sessionId, int diseaseId, Map<String, Object> diseaseParams) throws NodeNotFoundException {
        selectDisease(sessionId, diseaseId, diseaseParams, RelationLabel.INFERS);
    }


    protected void indicateDisease (long sessionId, int diseaseId, Map<String, Object> diseaseParams) throws NodeNotFoundException {
        selectDisease(sessionId, diseaseId, diseaseParams, RelationLabel.INDICATES);
    }

    protected void selectDisease(long sessionId, int diseaseId, Map<String, Object> diseaseParams, RelationLabel relation) throws NodeNotFoundException {
        Node sessionNode = getSessionOrException(sessionId);

        Node diseaseNode = getDiseaseOrException(diseaseId);
        try (Transaction tx = db.beginTx()) {

            Relationship session_disease = sessionNode.createRelationshipTo(diseaseNode, relation);
            setProperties(session_disease, diseaseParams);
            tx.success();
        }
    }


    protected void setUserInfo(long userId, Map<String, Object> userParams) throws NodeNotFoundException {
        Node userNode = getUserOrException(userId);

        try (Transaction tx = db.beginTx()) {

            setProperties(userNode, userParams);
            tx.success();
        }
    }

    protected Node newUser (long userId, Map<String, Object> userParams) {
        try (Transaction tx = db.beginTx()) {
            Node userNode = db.createNode(NodeLabel.USER);
            setProperties(userNode, userParams);
            userIndex.add(userNode, USER_ID_KEY, userId);
            tx.success();
            return userNode;
        }
    }

    protected Node getUserOrCreate (long userId) {
        Node userNode =  userIndex.get(USER_ID_KEY, userId).getSingle();
        if (userNode == null) {
            userNode = newUser(userId, Collections.EMPTY_MAP);
        }
        return userNode;
    }


    protected Node getUserOrException (long userId) throws NodeNotFoundException {
        Node userNode =  userIndex.get(USER_ID_KEY, userId).getSingle();
        if (userNode == null) {
            throw new NodeNotFoundException("User with id " + userId + " not found in database!");
        }
        return userNode;
    }


    protected Node getSessionOrException (long sessionId) throws NodeNotFoundException {
        Node sessionNode =  sessionIndex.get(SESSION_ID_KEY, sessionId).getSingle();
        if (sessionNode == null) {
            throw new NodeNotFoundException("Session with id " + sessionId + " not found in database!");
        }
        return sessionNode;
    }


    protected Node getDiseaseOrException (long diseaseId) throws NodeNotFoundException {
        Node diseaseNode =  sessionIndex.get(DISEASE_ID_KEY, diseaseId).getSingle();
        if (diseaseNode == null) {
            throw new NodeNotFoundException("Disease with id " + diseaseId + " not found in database!");
        }
        return diseaseNode;
    }


    protected Node getSymptomOrException (long symptomId) throws NodeNotFoundException {
        Node symptom =  sessionIndex.get(SYMPTOM_ID_KEY, symptomId).getSingle();
        if (symptom == null) {
            throw new NodeNotFoundException("Symptom with id " + symptomId + " not found in database!");
        }
        return symptom;
    }


    private PropertyContainer setProperties (PropertyContainer obj, final Map<String, Object> params) {
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                obj.setProperty(entry.getKey(), entry.getValue());
            }
        }

        return obj;
    }
}

