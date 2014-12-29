package actions;


import controllers.Users;
import models.NUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import repositories.SessionRepository;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 19:32
 */
@Service
public class SessionRequired extends Security.Authenticator {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public String getUsername(Context ctx) {

        try {
            Long sessionId = ctx.request().body().asJson().findValue("tk").asLong();

            long userId = Long.parseLong(ctx.session().get(Users.SESSION_USER_ID_KEY));

            long ts = Long.parseLong(ctx.session().get(Users.SESSION_LOGIN_TIME_KEY));

            NUser user = sessionRepository.getUserById(sessionId);
            if (user.refId == userId) {
                return Long.toString(userId);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized("Illegal token");
    }
}
