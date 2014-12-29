package repositories;

import models.NCheckup;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 15:23
 */
@RelationshipEntity(type = "DEPENDS_ON")
public interface CheckupRepository extends GraphRepository<NCheckup> {
}
