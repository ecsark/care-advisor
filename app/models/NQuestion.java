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
public class NQuestion extends AbstractEntity {

    @Indexed
    public String cnText;

    public Integer qType;

    @Fetch
    @RelatedToVia(elementClass = RAsk.class, type = "ASK")
    public Set<RAsk> questions=new HashSet<>();

    public RAsk addChoice (NSymptom symptom, String choiceText) {
        RAsk question = new RAsk(this, symptom);
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
