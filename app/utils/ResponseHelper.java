package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.JResponse;
import play.libs.Json;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 23:42
 */
public class ResponseHelper {

    public static JsonNode generate (Object object, int responseType) {
        ObjectNode response = Json.newObject();
        response.put("t", responseType);
        response.put("p", Json.toJson(object));
        return response;
    }

    public static JsonNode generate (JResponse object) {
        return generate(object, object.getResponseType());
    }
}
