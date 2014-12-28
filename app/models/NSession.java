package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 16:29
 */
@NodeEntity
public class NSession extends AbstractEntity {

    //TODO: add token to enhance security

    @RelatedTo(elementClass = NUser.class, type = "COMMITS", direction = Direction.INCOMING)
    public NUser user;

    @RelatedTo(elementClass = NSymptom.class, type = "REPORTS", direction = Direction.OUTGOING)
    public Set<NSymptom> symptoms = new LinkedHashSet<>();

    @RelatedTo(elementClass = NDisease.class, type = "DIAGNOSES", direction = Direction.OUTGOING)
    public Set<NDisease> diseases = new LinkedHashSet<>();

    @GraphProperty(propertyType = Long.class)
    public Timestamp created = new Timestamp(Calendar.getInstance().getTime().getTime());

    public NSymptom addSymptom (NSymptom symptom) {
        if (symptom == null)
            return null;
        //NSymptom symptom = new NSymptom();
        symptoms.add(symptom);
        return symptom;
    }
}
