package pl.piotrsukiennik.tuner.test.junitplain;

import junit.framework.TestCase;
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
@ContextConfiguration(locations = { "/jsqltuner-test-root-context-plain.xml" })
public class QueriesTest {

    @Resource
    private SQLQueryExecutionService queryExecutionService;

    //TODO - make valid to parse
    private static String TEST_TABLE_CREATE = "CREATE TABLE TestTable(id int,key varchar(255),value varchar(255))";

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
    @Repeat(3)
    public void runTestValidQueries() {
        Utils.processEachLine( getClass().getClassLoader(),
         "sql/jsqltuner-test-valid-queries.sql", new Utils.StringProcessor() {
            @Override
            public void process( String line ) {
                try {
                    queryExecutionService.execute( line );
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                    TestCase.fail();
                }
            }
        } );
    }


    @Test
    @Repeat(3)
    public void runTestInvocationInvalidQueries() {
        Utils.processEachLine( getClass().getClassLoader(),
         "sql/jsqltuner-test-invocation-invalid-queries.sql", new Utils.StringProcessor() {
            @Override
            public void process( String line ) {
                try {
                    queryExecutionService.execute( line );
                    TestCase.fail();
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    @Test
    @Repeat(2)
    public void runTestParserInvalidQueries() {
        Utils.processEachLine( getClass().getClassLoader(),
         "sql/jsqltuner-test-parser-invalid-queries.sql", new Utils.StringProcessor() {
            @Override
            public void process( String line ) {
                try {
                    queryExecutionService.execute( line );
                    TestCase.fail();
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

}
