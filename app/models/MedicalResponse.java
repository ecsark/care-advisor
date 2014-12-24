package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/18/14
 * Time: 00:28
 */
public class MedicalResponse implements JResponse {

    public MedicalQuestion createQuestion () {
        if (questions == null)
            questions = new ArrayList<>();
        MedicalQuestion question = new MedicalQuestion();
        questions.add(question);
        return question;
    }

    @JsonProperty("q")
    public List<MedicalQuestion> questions;

    @Override
    public int getResponseType() {
        return 3;
    }
}
