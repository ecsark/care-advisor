package controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Result importNode() {
        try {
            importer.importNodes(new File("/Users/ecsark/Projects/care-advisor/app/assets/node.txt"));
            return ok("All nodes imported");
        } catch (IOException e) {
            return badRequest(e.getMessage());
        }
    }

    public Result importRelationship() {
        try {
            importer.importRelationship(new File("/Users/ecsark/Projects/care-advisor/app/assets/relationship.txt"));
            return ok("All relationship imported");
        } catch (IOException e) {
            return badRequest(e.getMessage());
        }
    }
}
