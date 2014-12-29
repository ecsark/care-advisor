package models;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
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

    public RCause addSymptom (NSymptom symptom) {
        if (causingSymptoms == null)
            causingSymptoms = new HashSet<>();
        RCause cause = new RCause(this, symptom);
        causingSymptoms.add(cause);
        return cause;
    }
}
