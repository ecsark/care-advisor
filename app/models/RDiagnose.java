package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import java.util.Date;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 14:46
 */
@RelationshipEntity(type = "DIAGNOSE")
public class RDiagnose extends AbstractEntity {

    public RDiagnose(NSession session, NDisease disease) {
        this.session = session;
        this.disease = disease;
    }

    public RDiagnose() {}

    public Date reportTime = new Date(System.currentTimeMillis());

    public Date diagDate;

    @StartNode
    public NSession session;

    @EndNode
    public NDisease disease;

}
