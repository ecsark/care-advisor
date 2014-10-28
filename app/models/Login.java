package models;

import play.db.ebean.Model;
import utils.RandomString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Date;

/**
 * User: ecsark
 * Date: 10/28/14
 * Time: 22:30
 */

@Entity
public class Login extends Model {

    @Id
    public String loginId;

    public String userId;

    public Date loginDate;

    public String token;

    @Transient
    private static RandomString randString = new RandomString(8);

    public static Model.Finder<String, Login> find = new Model.Finder<String, Login>(String.class, Login.class);

    public static Login authenticate(String jtoken) {
        try {
            String[] pair = jtoken.split("=");
            Login login =  find.where().eq("loginId",pair[0]).findUnique();
            if (login != null && login.token.equals(pair[1]))
                return login;
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Login create(String userId) {
        Login login = new Login();
        try {
            login.userId = userId;
            login.loginDate = new Date(System.currentTimeMillis());
            login.token = randString.nextString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        login.save();
        return login;
    }

}
