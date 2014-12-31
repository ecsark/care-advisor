package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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

    @Indexed
    public Long refId;

    // 0: female, 1: male, other: unknown
    public Integer sex;

    public Date birthDate;

    @RelatedTo(elementClass = NSession.class, type = "COMMIT", direction = Direction.OUTGOING)
    public Set<NSession> sessions = new HashSet<>();

    public NSession newSession() {
        NSession session = new NSession();
        sessions.add(session);
        return session;
    }

    //@GraphProperty(propertyType = Long.class)
    public Date created = new Date(Calendar.getInstance().getTime().getTime());

    public NUser() {}
}
