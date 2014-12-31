package models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Date;
import java.util.HashSet;
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

    @Fetch
    @RelatedTo(elementClass = NUser.class, type = "COMMIT", direction = Direction.INCOMING)
    public NUser user;

    @RelatedTo(elementClass = NSymptom.class, type = "REPORT", direction = Direction.OUTGOING)
    public Set<NSymptom> symptoms = new HashSet<>();

    @RelatedToVia
    public Set<RDiagnose> diagnosed;

    //@GraphProperty(propertyType = Long.class)
    public Date created = new Date(System.currentTimeMillis());

    public NSymptom addSymptom (NSymptom symptom) {
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
