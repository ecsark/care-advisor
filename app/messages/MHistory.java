package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/24/14
 * Time: 17:14
 */
public class MHistory implements IMessage {

    @JsonProperty("h")
    public List<MRecord> history;

    public MRecord addHistory() {
        if (history == null)
            history = new ArrayList<>();
        MRecord record = new MRecord();
        history.add(record);
        return record;
    }

    @Override
    public int getResponseType() {
        return 4;
    }
}
