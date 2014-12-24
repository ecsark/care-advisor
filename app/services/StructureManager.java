package services;

import org.neo4j.server.rest.web.NodeNotFoundException;

import java.util.Map;

/**
 * User: ecsark
 * Date: 12/19/14
 * Time: 22:54
 */
public interface StructureManager {


    void newToken(String userId, String tokenId, Map<String, Object> tokenParams, Map<String, Object> relationParams) throws UserNotFoundException;

    void tellSymptom (String tokenId, int symptomId, Map<String, Object> params) throws NodeNotFoundException;

    void inferDisease (String tokenId, int diseaseId, Map<String, Object> params) throws NodeNotFoundException;

    void indicateDisease (String tokenId, int diseaseId, Map<String, Object> params) throws NodeNotFoundException;

    void setUserInfo (String userId, Map<String, Object> params);

    long newUser (String userId, Map<String, Object> params);
}
