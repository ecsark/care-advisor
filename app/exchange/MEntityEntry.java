package exchange;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 10:13
 */
public class MEntityEntry {

    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public Long id;

}
