package services;

import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;

/**
 * User: ecsark
 * Date: 12/22/14
 * Time: 22:04
 */
public class UserNotFoundException extends NodeNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(NotFoundException e) {
        super(e);
    }
}
