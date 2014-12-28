package models;

import org.springframework.data.neo4j.annotation.GraphId;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 19:55
 */
public class AbstractEntity {
    @GraphId
    public Long id;
}
