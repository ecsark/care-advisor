package services;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * User: ecsark
 * Date: 12/11/14
 * Time: 00:20
 */
public class CypherModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(CypherExecutor.class).to(RestCypherExecutor.class);
        bindConstant().annotatedWith(Names.named("Neo4j URL")).to("http://localhost:7474/db/data/");


    }


}
