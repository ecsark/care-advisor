package exchange;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: ecsark
 * Date: 12/25/14
 * Time: 00:24
 */
public class MAdvice implements MResponse {

    @JsonProperty("a_txt")
    public String content;

    @JsonProperty("a_id")
    public long id;

    @JsonProperty("do_tm")
    public long doTime;

    @JsonProperty("ck_tm")
    public long checkTime;

    @Override
    public int getResponseType() {
        return 5;
    }
}
