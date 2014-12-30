package messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: ecsark
 * Date: 12/18/14
 * Time: 00:28
 */
public class MDialogue implements MResponse {

    public MQuestion createQuestion () {
        if (questions == null)
            questions = new ArrayList<>();
        MQuestion question = new MQuestion();
        questions.add(question);
        return question;
    }

    @JsonProperty("q")
    public List<MQuestion> questions;

    @JsonIgnore
    public List<Long> getQuestionIds () {
        if (questions == null)
            return new ArrayList<>();
        return questions.stream().map(ans -> ans.questionId).collect(Collectors.toList());
    }

    @Override
    public int getResponseType() {
        return 3;
    }
}
