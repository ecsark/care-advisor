package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

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

    @RelatedTo(elementClass = NUser.class, type = "COMMIT", direction = Direction.INCOMING)
    public NUser user;

    @RelatedTo(elementClass = NSymptom.class, type = "REPORT", direction = Direction.OUTGOING)
    public Set<NSymptom> symptoms;

    @RelatedToVia
    public Set<RDiagnose> diagnosed;

    @GraphProperty(propertyType = Long.class)
    public Timestamp created = new Timestamp(Calendar.getInstance().getTime().getTime());

    public NSymptom addSymptom (NSymptom symptom) {
        if (symptom == null)
            return null;
        if (symptoms == null)
            symptoms = new LinkedHashSet<>();
        symptoms.add(symptom);
        return symptom;
    }

    public RDiagnose addDiagnosedDisease(NDisease disease) {
        if (disease == null)
            return null;
        if (diagnosed == null)
            diagnosed = new LinkedHashSet<>();
        RDiagnose diag = new RDiagnose(this, disease);
        diagnosed.add(diag);
        return diag;
    }
}
