package services;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * User: ecsark
 * Date: 12/15/14
 * Time: 01:06
 */
@SuppressWarnings("unchecked")
public class CypherTest {


    static CypherExecutor executor;

    @BeforeClass
    public static void setUp() {
        executor = new RestCypherExecutor("http://localhost:7474/db/data");
    }


    @Test
    public void testExecutor() {
        Iterator<Map<String,Object>> result = executor.query("MATCH n RETURN n", Collections.EMPTY_MAP);
        while (result.hasNext()) {
            Map<String, Object> row = result.next();
            System.out.println(row.toString());
        }

    }
}
