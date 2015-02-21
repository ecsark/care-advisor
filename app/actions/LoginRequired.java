package actions;


import controllers.Users;
import org.springframework.stereotype.Service;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 19:32
 */
@Service
public class LoginRequired extends Security.Authenticator {

    private static long expiration = 21600000;

    @Override
    public String getUsername(Context ctx) {

        try {
            long uid = Long.parseLong(ctx.session().get(Users.SESSION_USER_ID_KEY));
            long ts = Long.parseLong(ctx.session().get(Users.SESSION_LOGIN_TIME_KEY));
            if (System.currentTimeMillis() - ts > expiration)
                return null;
            //Date sessionDt = new Date(ts);
            return Long.toString(uid);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized("Please log in");
    }
}
