package repositories;

import models.NUser;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 16:44
 */
@Repository
public interface UserRepository extends GraphRepository<NUser> {

    NUser findByRefId(long refId);

}
