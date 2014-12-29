package models;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 11:59
 */

@NodeEntity
public class NDisease extends AbstractEntity {

    @Indexed
    public String cnText;

    @RelatedToVia
    public Set<RCause> causingSymptoms;

    @RelatedToVia
    public Iterable<RDiagnosis> diagnosedSessions;

    @RelatedToVia
    public Set<RDependsOn> dependentCheckups;
}
