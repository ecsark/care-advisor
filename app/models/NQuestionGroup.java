package models;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 19:53
 */
@NodeEntity
public class NQuestionGroup extends AbstractEntity {

    public String chText;

    @RelatedToVia
    Set<RQuestion> questions=new HashSet<>();

    public RQuestion addChoice (NSymptom symptom, String choiceText) {
        RQuestion question = new RQuestion();
        question.choiceText = choiceText;
        questions.add(question);
        return question;
    }
}
