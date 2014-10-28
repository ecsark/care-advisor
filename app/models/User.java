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
    public String id;
    public String password;

    public String name;

    public String role;

    public User() {}

    public static User create (String id, String password) {

        User user = new User();
        try {
            user.id = id;
            user.password = PasswordHash.createHash(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        user.save();
        return user;
    }


    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);

    public static User authenticate(String id, String password) {
        try {
            User user =  find.where().eq("id",id).findUnique();
            if (user != null && PasswordHash.validatePassword(password,user.password))
                return user;
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isUserExist (String id) {
        return find.where().eq("id",id).findUnique()!=null;
    }
}
