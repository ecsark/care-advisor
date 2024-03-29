package messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: ecsark
 * Date: 12/30/14
 * Time: 15:19
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MItem {

    @JsonProperty("a_id")
    public long id;

    @JsonProperty("a_txt")
    public String answerText;

    @JsonProperty("a_val")
    public Double answerValue;


}
