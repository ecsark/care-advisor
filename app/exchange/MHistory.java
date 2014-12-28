package exchange;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 17:14
 */
public class MHistory implements MResponse {

    @JsonProperty("h")
    public List<MRecord> history;

    public MRecord addHistory() {
        if (history == null)
            history = new ArrayList<>();
        MRecord record = new MRecord();
        history.add(record);
        return record;
    }

    public class MRecord {

        @JsonProperty("sid")
        public long sessionId;

        @JsonProperty("cre")
        public Timestamp createdTime;

        @JsonProperty("syms")
        public List<MObject> symptoms;

        @JsonProperty("dis")
        public List<MObject> diseases;

        public MRecord setSessionId (long sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public MRecord setCreatedTime(Timestamp createdTime) {
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

    @Override
    public int getResponseType() {
        return 4;
    }
}
