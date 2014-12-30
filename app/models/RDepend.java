package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 15:39
 */
@RelationshipEntity(type = "DEPEND")
public class RDepend extends AbstractEntity {

    public RDepend() {}

    public RDepend(NDisease disease, NCheckup checkup) {
        this.disease = disease;
        this.checkup = checkup;
    }

    public Double importance = 1.0;

    @StartNode
    public NDisease disease ;

    @EndNode
    public NCheckup checkup;
}
