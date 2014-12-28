package models;

import play.db.ebean.Model;
import utils.PasswordHash;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: ecsark
 * Date: 10/28/14
 * Time: 22:28 PM
 */

@Entity
public class MUser extends Model {

    @Id
    public long userId;
    public String username;
    public String password;

    public String name;
    public String contact;

    public MUser() {}

    public static MUser create (String username, String password) {

        MUser user = new MUser();
        try {
            user.username = username;
            user.password = PasswordHash.createHash(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        user.save();
        return user;
    }


    public MUser update (String newUsername, String newPassword) {

        MUser user = new MUser();
        try {
            user.username = newUsername;
            user.password = PasswordHash.createHash(newPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        user.save();
        return user;
    }


    public static Finder<String, MUser> find = new Finder<>(String.class, MUser.class);

    public static MUser authenticate(String username, String password) {
        if (username == null || password == null)
            return null;

        try {
            MUser user =  find.where().eq("username",username).findUnique();
            if (user != null && PasswordHash.validatePassword(password, user.password))
                return user;
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isUserExist (String username) {
        return find.where().eq("username", username).findUnique()!=null;
    }
}
