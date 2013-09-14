package pl.piotrsukiennik.tuner.test.query;


import org.junit.Test;
import pl.piotrsukiennik.tuner.cache.model.HashesHolderManager;
import pl.piotrsukiennik.tuner.cache.model.build.EnterableHolderFactory;
import pl.piotrsukiennik.tuner.execution.QueryExecutionBuilder;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.JSqlQueryParser;
import pl.piotrsukiennik.tuner.util.HashGenerators;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 17:41
 */

public class CacheManagerTest {
    @Test
    public void managerTest(){

        String[] path = new String[]{"DB","SCHEMA","TABLE","COLUMN"};
        EnterableHolderFactory holderFactory  = new EnterableHolderFactory(LinkedHashSet.class);
        HashesHolderManager<String,String> hashesHolderManager = new HashesHolderManager<String,String>(holderFactory);
        hashesHolderManager.put(path,"12345");
        Collection<String> col = hashesHolderManager.get(path);
        int a=1;
    }
}
