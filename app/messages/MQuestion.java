package messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/18/14
 * Time: 01:02
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MQuestion {

    @JsonProperty("q_id")
    public long questionId;

    @JsonProperty("q_txt")
    public String questionText;

    @JsonProperty("type")
    public Integer questionType;

    public MQuestion setQuestionId(long questionId) {
        this.questionId = questionId;
        return this;
    }

    public MQuestion setQuestionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public MQuestion setQuestionType(Integer questionType) {
        this.questionType = questionType;
        return this;
    }

    @JsonProperty("items")
    public List<MItem> items = new ArrayList<>();


    public MItem createItem() {

        MItem item = new MItem();
        items.add(item);
        return item;
    }


}
