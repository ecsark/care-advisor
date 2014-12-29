package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:25
 */
@NodeEntity
public class NSymptomGroup extends AbstractEntity{

    @Indexed
    public String cnText;

    @RelatedTo(type = "INCLUDES", direction = Direction.OUTGOING)
    public Set<NSymptom> symptoms;

    public NSymptom addSymptom (NSymptom symptom) {
        if (symptoms == null)
            symptoms = new HashSet<>();
        symptoms.add(symptom);
        return symptom;
    }
}
