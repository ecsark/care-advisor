package models;

import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 22:29
 */
@NodeEntity
public class NCheckup extends AbstractEntity {
    String content;
}
