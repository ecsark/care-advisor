package repositories;

import models.NSymptom;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 01:44
 */
@Repository
public interface SymptomRepository extends GraphRepository<NSymptom> {
    NSymptom getById (long id);
    NSymptom getByCnText (String cnText);


}
