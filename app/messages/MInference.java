package messages;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 12/25/14
 * Time: 00:14
 */
public class MInference implements MResponse {

    List<MObject> diseases;

    public MInference addDisease (MObject disease) {
        if (diseases == null)
            diseases = new ArrayList<>();
        diseases.add(disease);
        return this;
    }

    @Override
    public int getResponseType() {
        return 2;
    }
}
