package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 23:38
 */
public class MRecord {

    @JsonProperty("sid")
    public long sessionId;

    @JsonProperty("cre")
    public Date createdTime;

    @JsonProperty("sym")
    public List<MObject> symptoms;

    @JsonProperty("dis")
    public List<MObject> diseases;

    public MRecord setSessionId (long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public MRecord setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public MObject addSymptom () {
        if (symptoms == null)
            symptoms = new ArrayList<>();
        MObject obj = new MObject();
        symptoms.add(obj);
        return obj;
    }

    public MObject addDisease () {
        if (diseases == null)
            diseases = new ArrayList<>();
        MObject obj = new MObject();
        diseases.add(obj);
        return obj;
    }

}