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
public class User extends Model {

    @Id
    public long userId;
    public String username;
    public String password;

    //public String name;

    public User() {}

    public static User create (String username, String password) {

        User user = new User();
        try {
            user.username = username;
            user.password = PasswordHash.createHash(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        user.save();
        return user;
    }


    public User update (String newUsername, String newPassword) {

        User user = new User();
        try {
            user.username = newUsername;
            user.password = PasswordHash.createHash(newPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        user.save();
        return user;
    }


    public static Finder<String, User> find = new Finder<>(String.class, User.class);

    public static User authenticate(String username, String password) {
        try {
            User user =  find.where().eq("username",username).findUnique();
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
