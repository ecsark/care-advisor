package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 23:42
 */
public class MUserInfo implements IMessage {
    @JsonProperty("sex")
    public Integer sex;

    @JsonProperty("b_dt")
    public Date birthdate;

    @Override
    public int getResponseType() {
        return 7;
    }
}
