package models;

import org.neo4j.graphdb.RelationshipType;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 12:16
 */
public enum RelationLabel implements RelationshipType {
    COMMITS, EXPERIENCES, SHOWS, INDICATES, INFERS;

}
