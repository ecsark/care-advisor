package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ask;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Care for you~"));
    }


    public static Result indexAsk() {
        return ok(ask.render("IntelDiagnosis"));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result jsonHello() {
        JsonNode json = request().body().asJson();
        if (json==null) {
            return badRequest();
        }
        String name = json.findPath("name").textValue();
        if (name==null) {
            return badRequest("Missing parameter [name]");
        }

        ObjectNode result = Json.newObject();
        result.put("greeting","Hello "+name);
        result.put("version","1.0");
        return ok(result);
    }

    public static Result preflight(String all) {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Allow", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
        return ok();
    }
}

