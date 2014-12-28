package exchange;

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

    public static int SINGLE_CHOICE = 0;
    public static int MULTIPLE_CHOICES = 1;
    public static int INTEGER_RANGE = 2;
    public static int FLOAT_RANGE = 3;

    @JsonProperty("q_id")
    public int questionId;

    @JsonProperty("q_txt")
    public String questionText;

    @JsonProperty("type")
    public int questionType;

    public MQuestion setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public MQuestion setQuestionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public MQuestion setQuestionType(int questionType) {
        this.questionType = questionType;
        return this;
    }

    @JsonProperty("opt")
    public List<MedicalChoice> options;

    public MQuestion() {}

    public MedicalChoice createChoice () {
        if (options == null)
            options = new ArrayList<>();
        MedicalChoice choice = new MedicalChoice();
        options.add(choice);
        return choice;
    }

    public class MedicalChoice {

        public MedicalChoice() {}
        public MedicalChoice(int answerId, String answerText) {
            this.answerId = answerId;
            this.answerText = answerText;
        }

        @JsonProperty("a_id")
        public int answerId;

        @JsonProperty("a_txt")
        public String answerText;

        public MedicalChoice setAnswerId(int answerId) {
            this.answerId = answerId;
            return this;
        }

        public MedicalChoice setAnswerText(String answerText) {
            this.answerText = answerText;
            return this;
        }
    }
}
