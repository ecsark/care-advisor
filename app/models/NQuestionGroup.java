package models;

import org.springframework.data.neo4j.annotation.Indexed;
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

    @Indexed
    public String cnText;

    @RelatedToVia
    public Set<RQuestion> questions=new HashSet<>();

    public RQuestion addChoice (NSymptom symptom, String choiceText) {
        RQuestion question = new RQuestion(this, symptom);
        question.cnText = choiceText;
        questions.add(question);
        return question;
    }
}
