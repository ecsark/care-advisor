package models;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/18/14
 * Time: 00:28
 */
public class MedicalResponse {

    public MedicalQuestion createQuestion () {
        if (q == null)
            q = new ArrayList<>();
        MedicalQuestion question = new MedicalQuestion();
        q.add(question);
        return question;
    }

    public List<MedicalQuestion> q;
}
