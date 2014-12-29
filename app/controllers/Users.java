package controllers;

import actions.LoginRequired;
import com.fasterxml.jackson.databind.JsonNode;
import models.MFeedback;
import models.MUser;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:06
 */
public class Users extends Controller {

    public static final String SESSION_USER_ID_KEY = "uid";
    public static final String SESSION_LOGIN_TIME_KEY = "lg_tm";

    private static Calendar calendar = Calendar.getInstance();

    @BodyParser.Of(BodyParser.Json.class)
    public static Result signup() {

        try {
            JsonNode json = request().body().asJson();
            String username = json.findValue("usr").textValue();
            String password = json.findValue("pwd").textValue();

            if (MUser.isUserExist(username))
                return forbidden("Username already exists");

            MUser.create(username, password);

            return ok("User " + username + " has been created successfully");

        } catch(NullPointerException e) {
            return badRequest();
        }
    }

    @Security.Authenticated(LoginRequired.class)
    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateUser() {

        try {
            JsonNode json = request().body().asJson();
            String username = json.findValue("usr").textValue();
            String password = json.findValue("pwd").textValue();
            MUser user = MUser.authenticate(username, password);
            if (user == null || user.userId != getUserId ())
                return unauthorized("Incorrect username or password");

            String newUserName = json.findValue("nusr").textValue();
            String newPassword = json.findValue("npwd").textValue();
            if (newUserName.equals("") || newPassword.equals(""))
                return forbidden("Username or password should not be empty");

            user.update(newUserName, newPassword);
            return ok("User information updated");

        } catch (NullPointerException e) {
            return badRequest();
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result login() {
        try {
            JsonNode json = request().body().asJson();
            String username = json.findValue("usr").textValue();
            String password = json.findValue("pwd").textValue();

            MUser user = MUser.authenticate(username, password);
            if (user == null)
                return unauthorized("Incorrect username or password");

            session().clear();
            session(SESSION_USER_ID_KEY, Long.toString(user.userId));
            session(SESSION_LOGIN_TIME_KEY, Long.toString(System.currentTimeMillis()));

            return ok();

        } catch (NullPointerException e) {
            return badRequest();
        }
    }

    public static long getUserId () {
        return Long.parseLong(session(SESSION_USER_ID_KEY));
    }

    public static Result logout() {
        session().clear();
        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result feedback() {
        JsonNode json = request().body().asJson();
        MFeedback feedback = new MFeedback();
        feedback.content = json.findValue("content").textValue();
        if (feedback.content == null || feedback.content.equals(""))
            return badRequest("Content should not be null.");
        feedback.createdTime = new Timestamp(calendar.getTime().getTime());
        if (session(SESSION_USER_ID_KEY) != null)
            feedback.userId = Long.parseLong(session(SESSION_USER_ID_KEY));
        feedback.contact = json.findValue("contact").textValue();
        feedback.email = json.findValue("email").textValue();
        feedback.save();

        return ok();
    }
}
