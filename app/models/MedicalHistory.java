package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 17:14
 */
public class MedicalHistory implements JResponse {

    @JsonProperty("sid")
    long session_id;

    @JsonProperty("cre")
    Timestamp created_ts;

    @JsonProperty("syms")
    List<MedicalObject> symptoms;

    @JsonProperty("dis")
    List<MedicalObject> diseases;

    @Override
    public int getResponseType() {
        return 4;
    }
}
