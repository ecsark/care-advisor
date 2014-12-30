package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 10:13
 */
public class MEntityEntry {

    @JsonProperty("d_txt")
    public String name;

    @JsonProperty("d_id")
    public Long id;

    @JsonProperty("d_eval")
    public Double value;
}
