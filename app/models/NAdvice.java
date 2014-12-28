package models;

import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:30
 */
@NodeEntity
public class NAdvice extends AbstractEntity {
    String content;
}
