package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Login;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:09
 */
public class Medical extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result ask() {

        JsonNode json = request().body().asJson();

        try {

            String jToken = json.get("token").textValue();
            Login lg = Login.authenticate(jToken);
            lg.loginDate = new Date(System.currentTimeMillis());
            lg.save();
            ObjectNode inquiry = analyze(json);
            ObjectNode result = Json.newObject();
            result.put("inquiry",inquiry);
            return ok(result);
        } catch (NullPointerException e) {
            return badRequest("Invalid credential");
        } catch (IllegalArgumentException e) {
            return badRequest("ask ill formatted");
        }

    }

    private static ObjectNode analyze(JsonNode json) {
        JsonNode symps = json.get("symptom");
        List<String> sympList = new ArrayList<String>();
        if (symps!=null && symps.isArray()) {
            for (JsonNode symp : symps) {
                sympList.add(symp.textValue());
            }
        } else {
            throw new IllegalArgumentException("symptom ill formatted");
        }
        //TODO
        ObjectNode inquiry = Json.newObject();
        inquiry.put("q","Have you ever caught a cold in the past week?");
        inquiry.put("r", 5);
        return inquiry;
    }
}
