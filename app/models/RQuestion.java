package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 20:47
 */
@RelationshipEntity(type = "ASKS")
public class RQuestion extends AbstractEntity {
    public String cnText;

    @StartNode
    public NQuestionGroup questionGroup;

    @EndNode
    public NSymptom symptomChoice;

    public RQuestion(NQuestionGroup questionGroup, NSymptom symptomChoice) {
        this.questionGroup = questionGroup;
        this.symptomChoice = symptomChoice;
    }

    public RQuestion() {}
}
