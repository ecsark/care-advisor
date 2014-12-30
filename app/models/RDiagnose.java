package models;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import java.sql.Timestamp;
import java.util.Date;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 14:46
 */
@RelationshipEntity(type = "CAUSE")
public class RDiagnose extends AbstractEntity {

    public RDiagnose(NSession session, NDisease disease) {
        this.session = session;
        this.disease = disease;
    }

    public RDiagnose() {}

    public Timestamp reportTime = new Timestamp(System.currentTimeMillis());

    public Date diagDate;

    @StartNode
    public NSession session;

    @EndNode
    public NDisease disease;

}
