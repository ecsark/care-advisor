package models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import play.libs.Json;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MedicalObjectTest {

    private static ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    @Test
    public void testSerialize () {
        MedicalObject obj = new MedicalObject();
        obj.params = new HashMap<>();
        obj.id=567;
        obj.name="2352";
        obj.params.put("abc",123);
        obj.params.put("sdf","web");

        MedicalResponse mp = new MedicalResponse();
        mp.createQuestion().questionId = 125;

        JsonNode serialized = mapper.valueToTree(obj);
        MedicalObject obj2 = Json.fromJson(serialized, MedicalObject.class);
        assertEquals(obj.id, obj2.id);
        assertNotNull(obj2.params.get("sdf"));

    }
}