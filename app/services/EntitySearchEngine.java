package services;

import exchange.MEntityEntry;
import models.NodeLabel;
import org.neo4j.graphdb.GraphDatabaseService;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: ecsark
 * Date: 12/27/14
 * Time: 21:30
 */
@Singleton
public class EntitySearchEngine {

    //@Inject
    private GraphDatabaseService db;

    private List<MEntityEntry> diseaseLookup;

    public EntitySearchEngine () {
        //diseaseLookup = loadDiseases();

    }

    private List<MEntityEntry> loadDiseases () {
        List<MEntityEntry> diseases = new ArrayList<>();
        db.findNodesByLabelAndProperty(NodeLabel.DISEASE, null, null);
        return diseases;
    }


    public List<MEntityEntry> searchDiseases (String query) {
        List<MEntityEntry> candidates = searchMatchedDiseases(query);
        if (candidates.size() < 1) {
            candidates = searchRelatedDiseases(query);
        }
        return candidates;
    }

    public List<MEntityEntry> searchMatchedDiseases (String query) {
        return diseaseLookup.stream()
                .filter(d -> containAllCharacters(d.name, query)).collect(Collectors.toList());
    }


    public List<MEntityEntry> searchRelatedDiseases (String query) {
        return diseaseLookup.stream()
                .filter(d -> containACharacter(d.name, query)).collect(Collectors.toList());
    }

    private static boolean containACharacter(String candidate, String query) {
        for (char q : query.toCharArray()) {
            if (candidate.contains(String.valueOf(q)))
                return true;
        }
        return false;
    }

    private static boolean containAllCharacters(String candidate, String query) {
        for (char q : query.toCharArray()) {
            if (!candidate.contains(String.valueOf(q)))
                return false;
        }
        return true;
    }


}
