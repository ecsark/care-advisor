package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 16:25
 */
@NodeEntity
public class NUser extends AbstractEntity {

    @Transient
    public static final String REF_ID_INDEX = "refId";

    @Indexed(unique = true)
    public Long refId;

    // 0: female, 1: male, other: unknown
    public Integer sex;

    public Date birthDate;

    @RelatedTo(elementClass = NSession.class, type = "COMMITS", direction = Direction.OUTGOING)
    public Set<NSession> sessions = new LinkedHashSet<>();

    public NSession newSession() {
        NSession session = new NSession();
        sessions.add(session);
        return session;
    }

    @GraphProperty(propertyType = Long.class)
    public Timestamp created = new Timestamp(Calendar.getInstance().getTime().getTime());

}
