package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/18/14
 * Time: 00:28
 */
public class MFurtherQuestions implements MResponse {

    public MQuestion createQuestion () {
        if (questions == null)
            questions = new ArrayList<>();
        MQuestion question = new MQuestion();
        questions.add(question);
        return question;
    }

    @JsonProperty("q")
    public List<MQuestion> questions;

    @Override
    public int getResponseType() {
        return 3;
    }
}
