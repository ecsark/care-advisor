package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:25
 */
@NodeEntity
public class NSymptomGroup extends AbstractEntity{

    String chText;

    @RelatedTo(type = "INCLUDES", direction = Direction.OUTGOING)
    public Set<NSymptom> symptoms = new LinkedHashSet<>();


}
