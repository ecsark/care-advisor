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

    @BodyParser.Of(BodyParser.Json.class)
    public static Result login() {
        JsonNode json = request().body().asJson();
        Login lg;
        try {
            String username = json.get("username").textValue();
            String password = json.get("password").textValue();
            User user = User.authenticate(username, password);
            lg = Login.create(user.id);
        } catch (NullPointerException e) {
            return badRequest("Invalid credential");
        }
        ObjectNode result = Json.newObject();
        result.put("token",lg.loginId+"="+lg.token);
        result.put("version","1.0");
        return ok(result);
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result signup() {
        JsonNode json = request().body().asJson();
        try {
            String username = json.get("username").textValue();
            String password = json.get("password").textValue();
            if (User.isUserExist(username))
                return ok("Username already exists");

            User.create(username, password);
            return ok("User " + username + " has been created successfully");
        } catch(NullPointerException e) {
            return badRequest("Invalid request");
        }
    }
}
