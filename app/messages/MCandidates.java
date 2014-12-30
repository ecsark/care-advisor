package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/28/14
 * Time: 10:07
 */
public class MCandidates implements IMessage {

    public MCandidates () {}

    public MCandidates (List<MEntityEntry> entities) {
        this.entities = entities;
    }

    public MEntityEntry addEntityEntry () {
        if (entities == null)
            entities = new ArrayList<>();
        MEntityEntry entity = new MEntityEntry();
        entities.add(entity);
        return entity;
    }

    @JsonProperty("ent")
    List<MEntityEntry> entities;

    @Override
    public int getResponseType() {
        return 6;
    }
}
