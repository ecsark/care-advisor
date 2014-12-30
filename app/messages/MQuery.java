package messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: ecsark
 * Date: 12/30/14
 * Time: 20:02
 */
public class MQuery implements IMessage {

    public MAnswer createQuestion () {
        MAnswer question = new MAnswer();
        answers.add(question);
        return question;
    }

    @JsonProperty("a")
    public List<MAnswer> answers = new ArrayList<>();

    @JsonProperty("ma")
    public MAnswer mainAnswer = new MAnswer();

    @JsonProperty("status")
    public int status = AUTO;

    @JsonIgnore
    public List<Long> getQuestionIds () {
        return answers.stream().map(ans -> ans.questionId).collect(Collectors.toList());
    }


    @Transient
    public static final int AUTO = 0;
    @Transient
    public static final int DIAGNOSIS = 1;
    @Transient
    public static final int ADVICE = 2;
    @Transient
    public static final int MORE_QUESTION = 3;


    @Override
    public int getResponseType() {
        return 8;
    }
}
