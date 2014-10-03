package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Care for you~"));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result jsonHello() {
        JsonNode json = request().body().asJson();
        if (json==null) {
            return badRequest("Expecting Json data");
        }
        String name = json.findPath("name").textValue();
        if (name==null) {
            return badRequest("Missing parameter [name]");
        }

        ObjectNode result = Json.newObject();
        result.put("greeting","Hello "+name);
        return ok(result);

    }
}

