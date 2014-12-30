package models;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.Fetch;
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

    public Integer qType;

    @Fetch
    @RelatedToVia
    public Set<RQuestion> questions=new HashSet<>();

    public RQuestion addChoice (NSymptom symptom, String choiceText) {
        RQuestion question = new RQuestion(this, symptom);
        question.cnText = choiceText;
        questions.add(question);
        return question;
    }

    @Transient public static final int SINGLE_CHOICE = 0;
    @Transient public static final int MULTIPLE_CHOICES = 1;
    @Transient public static final int BINARY = 2;
    @Transient public static final int INTEGER_RANGE = 3;
    @Transient public static final int FLOAT_RANGE = 4;
}
