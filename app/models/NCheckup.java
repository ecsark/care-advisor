package models;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:29
 */
@NodeEntity
public class NCheckup extends AbstractEntity {
    @Indexed
    public String cnText;

    @RelatedToVia
    public Set<RDepend> dependedDiseases = new HashSet<>();

    public RDepend addDisease(NDisease disease) {
        RDepend dependant = new RDepend(disease, this);
        dependedDiseases.add(dependant);
        return dependant;
    }

    public Double priceLow;
    public Double priceHigh;
}
