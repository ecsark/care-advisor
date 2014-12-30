package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 13:56
 */
@RelationshipEntity(type = "CAUSES")
public class RCause extends AbstractEntity {

    public Integer operator;

    public Double value;

    @StartNode
    public NDisease disease;

    @EndNode
    public NSymptom symptom;

    public NDisease getDisease() {
        return disease;
    }

    public NSymptom getSymptom() {
        return symptom;
    }

    public RCause (NDisease disease, NSymptom symptom) {
        this.disease = disease;
        this.symptom = symptom;
    }

    public RCause() {}
}
