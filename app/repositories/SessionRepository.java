package repositories;

import models.NSession;
import models.NUser;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * User: ecsark
 * Date: 12/29/14
 * Time: 01:50
 */
public interface SessionRepository extends GraphRepository<NSession> {

    NUser getUserById(long sessionId);
}
