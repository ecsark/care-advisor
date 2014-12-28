package models;

import org.neo4j.graphdb.Label;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 12:16
 */
public enum NodeLabel implements Label {

    USER, TOKEN, DISEASE, SYMPTOM, QUESTION;
}
