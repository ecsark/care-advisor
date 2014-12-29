package repositories;

import models.NSymptomGroup;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 15:22
 */
public interface SymptomGroupRepository extends GraphRepository<NSymptomGroup> {

    NSymptomGroup getByCnText (String cnText);
}
