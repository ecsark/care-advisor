package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 20:47
 */
@RelationshipEntity(type = "ASK")
public class RAsk extends AbstractEntity {
    public String cnText;

    @Fetch
    @StartNode
    public NQuestion question;

    @EndNode
    public NSymptom symptomChoice;

    public RAsk(NQuestion question, NSymptom symptom) {
        this.question = question;
        this.symptomChoice = symptom;
    }

    public RAsk() {}
}
