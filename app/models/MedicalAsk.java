package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 23:38
 */
public class MedicalAsk {

    @JsonProperty("tk")
    public String token;

    @JsonProperty("c")
    public List<Integer> choices;
}
