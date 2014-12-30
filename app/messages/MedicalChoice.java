package messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: ecsark
 * Date: 12/30/14
 * Time: 15:19
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalChoice {

    @JsonProperty("a_id")
    public long answerId;

    @JsonProperty("a_txt")
    public String answerText;

    public MedicalChoice setAnswerId(long answerId) {
        this.answerId = answerId;
        return this;
    }

    public MedicalChoice setAnswerText(String answerText) {
        this.answerText = answerText;
        return this;
    }
}
