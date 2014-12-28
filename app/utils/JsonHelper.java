package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import exchange.MResponse;
import play.libs.Json;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 23:42
 */
public class JsonHelper {

    public static JsonNode generate (Object object, int responseType) {
        ObjectNode response = Json.newObject();
        response.put("t", responseType); // type
        response.put("p", Json.toJson(object)); // payload
        return response;
    }

    public static JsonNode generate (MResponse object) {
        return generate(object, object.getResponseType());
    }

}
