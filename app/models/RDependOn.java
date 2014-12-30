package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 15:39
 */
@RelationshipEntity(type = "DEPEND_ON")
public class RDependOn extends AbstractEntity {

    public RDependOn() {}

    public RDependOn(NDisease disease, NCheckup checkup) {
        this.disease = disease;
        this.checkup = checkup;
    }

    @StartNode
    public NDisease disease ;

    @EndNode
    public NCheckup checkup;
}
