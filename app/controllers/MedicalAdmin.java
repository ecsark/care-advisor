package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.GraphImporter;

import java.io.File;
import java.io.IOException;

/**
 * User: ecsark
 * Date: 12/30/14
 * Time: 00:56
 */
@org.springframework.stereotype.Controller
public class MedicalAdmin extends Controller {

    @Autowired
    private GraphImporter importer;


    public Result clearAll() {
        importer.clearAll();
        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result importNode() {
        try {
            JsonNode json = request().body().asJson();
            String fileName = json.findValue("f").asText();
            importer.importNodes(new File(fileName));
            return ok("All nodes imported");
        } catch (IOException e) {
            return badRequest(e.getMessage());
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result importRelationship() {
        try {
            JsonNode json = request().body().asJson();
            String fileName = json.findValue("f").asText();
            importer.importRelationship(new File(fileName));
            return ok("All relationship imported");
        } catch (IOException e) {
            return badRequest(e.getMessage());
        }
    }
}
