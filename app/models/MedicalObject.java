package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 17:18
 */
public class MedicalObject {

    @JsonProperty("name")
    String name;
    
    @JsonProperty("id")
    int id;

    @JsonProperty("p")
    Map<String, Object> params;
}
