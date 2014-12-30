package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 16:37
 */
@NodeEntity
public class NSymptom extends AbstractEntity {

    @Indexed
    public String cnText;

    @RelatedToVia (type = "CAUSES", elementClass = RCause.class, direction = Direction.INCOMING)
    public Set<RCause> causedBy;

    @RelatedTo (elementClass = NSession.class)
    public Set<NSession> sessions;

    @RelatedToVia(type="ASKS", elementClass = RQuestion.class, direction = Direction.INCOMING)
    public RQuestion question;
}
