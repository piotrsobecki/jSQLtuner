package pl.piotrsukiennik.tuner.test.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
import pl.piotrsukiennik.tuner.test.util.Utils;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jsqltuner-test-root-context.xml" })
public class QueriesTest {

    @Resource
    private SQLQueryExecutionService queryExecutionService;

    private static String TEST_TABLE_CREATE = "CREATE TABLE TestTable(id INT, key VARCHAR, value VARCHAR)";

    private static String TEST_TABLE_DROP = "DROP TABLE TestTable";

    @Before
    public void prepareDB() {

        try {
            queryExecutionService.execute( TEST_TABLE_CREATE );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanDB() {

        try {
            queryExecutionService.execute( TEST_TABLE_DROP );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }


    @Test
    @Repeat(10)
    public void runTestQueries() {
        Utils.processEachLine( getClass().getClassLoader(),
         "jsqltuner-test-queries.sql", new Utils.StringProcessor() {
            @Override
            public void process( String line ) {
                try {
                    queryExecutionService.execute( line );
                }
                catch ( Exception e ) {
                    //   e.printStackTrace();
                }
            }
        } );
    }


}
