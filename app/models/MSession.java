package models;

import play.db.ebean.Model;
import utils.RandomString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * User: ecsark
 * Date: 10/28/14
 * Time: 22:30
 */

@Entity
public class MSession extends Model {

    @Id
    public long sessionId;

    public long userId;

    public Timestamp createdTime;

    public String token;

    @Transient
    private static Calendar calendar = Calendar.getInstance();

    @Transient
    private static Timestamp currentTime () {
        return new Timestamp(calendar.getTime().getTime());
    }


    @Transient
    private static RandomString randString = new RandomString(12);

    public static Model.Finder<String, MSession> find = new Model.Finder<>(String.class, MSession.class);

    public static MSession authenticate(String token) {
        if (token == null)
            return null;
        try {
            String[] pair = token.split("=");
            MSession MSession =  find.where().eq("sessionId",pair[0]).findUnique();
            if (MSession != null && MSession.token.equals(pair[1]))
                return MSession;
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static MSession create(long userId) {
        MSession MSession = new MSession();
        try {
            MSession.userId = userId;
            MSession.createdTime = currentTime();
            MSession.token = randString.nextString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MSession.save();
        return MSession;
    }

}
