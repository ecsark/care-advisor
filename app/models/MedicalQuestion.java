package models;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/18/14
 * Time: 01:02
 */
public class MedicalQuestion {

    public static int SINGLE_CHOICE = 0;
    public static int MULTIPLE_CHOICES = 1;
    public static int INTEGER_RANGE = 2;
    public static int FLOAT_RANGE = 3;

    public int q_id;
    public String q_txt;
    public int question_type;

    public MedicalQuestion setQuestionId(int q_id) {
        this.q_id = q_id;
        return this;
    }

    public MedicalQuestion setQuestionText(String q_txt) {
        this.q_txt = q_txt;
        return this;
    }

    public MedicalQuestion setQuestionType(int question_type) {
        this.question_type = question_type;
        return this;
    }

    public List<MedicalChoice> opt;

    public MedicalQuestion() {}

    public MedicalChoice createChoice () {
        if (opt == null)
            opt = new ArrayList<>();
        MedicalChoice choice = new MedicalChoice();
        opt.add(choice);
        return choice;
    }

    public class MedicalChoice {

        public MedicalChoice() {}
        public MedicalChoice(int answer_id, String answer_text) {
            a_id = answer_id;
            a_txt = answer_text;
        }

        public int a_id;
        public String a_txt;

        public MedicalChoice setAnswerId(int answer_id) {
            a_id = answer_id;
            return this;
        }

        public MedicalChoice setAnswerText(String answer_text) {
            a_txt = answer_text;
            return this;
        }
    }
}
