package models;

/**
 * User: ecsark
 * Date: 12/25/14
 * Time: 00:24
 */
public class Recommendation implements JResponse {

    public String content;
    public long id;

    public long doTime;
    public long checkTime;

    @Override
    public int getResponseType() {
        return 5;
    }
}
