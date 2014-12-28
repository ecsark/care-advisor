package exchange;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 10:07
 */
public class MCandidates implements MResponse {

    public MCandidates () {}

    public MCandidates (List<MEntityEntry> entities) {
        this.entities = entities;
    }

    public MCandidates addEntityEntry (MEntityEntry entity) {
        if (entities == null)
            entities = new ArrayList<>();
        entities.add(entity);
        return this;
    }

    @JsonProperty("ent")
    List<MEntityEntry> entities;

    @Override
    public int getResponseType() {
        return 6;
    }
}
