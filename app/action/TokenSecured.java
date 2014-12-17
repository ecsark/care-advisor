package action;


import controllers.Users;
import models.Login;
import play.cache.Cache;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * User: ecsark
 * Date: 12/17/14
 * Time: 19:32
 */

public class TokenSecured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        String jToken;

        try {
            jToken = ctx.request().body().asJson().get("tk").textValue().toString();
        } catch (Exception e) {
            return null;
        }

        Login login = (Login) Cache.get(Users.tokenKey(jToken));
        if (login == null) {
            login = Login.authenticate(jToken);
            if (login != null)
                Cache.set(Users.tokenKey(jToken), login);
            else
                return null;
        }

        return jToken;
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return unauthorized("Illegal token");
    }
}
