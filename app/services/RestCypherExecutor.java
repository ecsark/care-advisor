package services;

import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Iterator;
import java.util.Map;

/**
 * User: ecsark
 * Date: 12/15/14
 * Time: 00:29
 */

@Singleton
public class RestCypherExecutor implements CypherExecutor {

    private RestAPI graphDb;

    @Inject
    public RestCypherExecutor(@Named("Neo4j URL") String url) {
        graphDb = new RestAPIFacade(url);
    }

    @Override
    public Iterator<Map<String, Object>> query(String statement, Map<String, Object> params) {
        QueryResult<Map<String, Object>> result = new RestCypherQueryEngine(graphDb).query(statement, params);
        return result.iterator();
    }
}
