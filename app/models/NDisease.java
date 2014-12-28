package models;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 11:59
 */

@NodeEntity
public class NDisease extends AbstractEntity {

    @Indexed
    public String cnText;

    @RelatedTo
    public Iterable<NSession> sessions;



}
