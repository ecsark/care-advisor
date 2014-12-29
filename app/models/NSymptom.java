package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 16:37
 */
@NodeEntity
public class NSymptom extends AbstractEntity {

    @Indexed
    public String cnText;

    @RelatedToVia (elementClass = RCause.class, direction = Direction.INCOMING)
    public Iterable<RCause> causedBy;

    public Iterable<NSession> sessions;

    @RelatedTo(type="ASKS")
    public NQuestionGroup question;
}
