package services;

import java.util.Iterator;
import java.util.Map;

/**
 * User: ecsark
 * Date: 12/10/14
 * Time: 15:45
 */
public interface CypherExecutor {
    Iterator<Map<String,Object>> query(String statement, Map<String,Object> params);
}