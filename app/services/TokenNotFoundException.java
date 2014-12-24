package services;

import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;

/**
 * User: ecsark
 * Date: 12/22/14
 * Time: 22:04
 */
public class TokenNotFoundException extends NodeNotFoundException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(NotFoundException e) {
        super(e);
    }
}
