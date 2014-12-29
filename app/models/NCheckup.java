package models;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:29
 */
@NodeEntity
public class NCheckup extends AbstractEntity {
    public String cnText;

    @RelatedToVia
    public Set<RDependsOn> dependedDiseases;
}
