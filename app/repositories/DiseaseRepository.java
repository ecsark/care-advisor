package repositories;

import models.NDisease;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 13:16
 */
public interface DiseaseRepository extends GraphRepository<NDisease> {
}
