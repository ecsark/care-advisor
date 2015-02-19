package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/30/14
 * Time: 19:54
 */
public class MAnswer {

    @JsonProperty("q_id")
    public long questionId;

    @JsonProperty("items")
    public List<MItem> items = new ArrayList<>();

    public void addItem(MItem item) {
        items.add(item);
    }

    public MItem createItem() {
        MItem item = new MItem();
        items.add(item);
        return item;
    }
}
