package models;

import java.util.List;

/**
 * User: ecsark
 * Date: 12/25/14
 * Time: 00:14
 */
public class MedicalFeeback implements JResponse {

    List<MedicalObject> diseases;

    @Override
    public int getResponseType() {
        return 2;
    }
}
