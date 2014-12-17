package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Login;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:06
 */
public class Users extends Controller {

    public static String tokenKey (String jToken) {
        return jToken + "_lg.key";
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newToken() {
        JsonNode json = request().body().asJson();
        String username, password;
        Login lg;
        try {
            username = json.get("usr").textValue().toString();
            password = json.get("pwd").textValue().toString();
        } catch (NullPointerException e) {
            return badRequest("Invalid credential");
        }

        try {
            User user = User.authenticate(username, password);
            lg = Login.create(user.id);
        } catch (NullPointerException e) {
            return unauthorized("Incorrect username or password");
        }

        ObjectNode result = Json.newObject();
        result.put("tk",lg.loginId+"="+lg.token);
        //result.put("v","0.1");
        return ok(result);
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result signup() {
        JsonNode json = request().body().asJson();
        try {
            // fields required
            String username = json.get("usr").textValue().toString();
            String password = json.get("pwd").textValue().toString();
            if (User.isUserExist(username))
                return ok("Username already exists");

            User.create(username, password);
            return ok("User " + username + " has been created successfully");
        } catch(NullPointerException e) {
            return badRequest("Invalid request");
        }
    }
}
