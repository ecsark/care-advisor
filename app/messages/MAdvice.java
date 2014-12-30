package messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/25/14
 * Time: 00:24
 */
public class MAdvice implements IMessage {

    @JsonProperty("ck")
    public List<MCheckup> checkups = new ArrayList<>();

    public MCheckup addCheckup () {
        MCheckup obj = new MCheckup();
        checkups.add(obj);
        return obj;
    }

    @Override
    public int getResponseType() {
        return 5;
    }
}
