package pl.piotrsukiennik.tuner.test.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/jsqltuner-test-root-context.xml" })
public abstract class TestQueries extends AbstractSQLFramework {

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
    @Repeat( 3 )
    public void runTestValidQueries() {
        testEachValidStatement( "runTestValidQueries()", "sql/jsqltuner-test-valid-queries.sql" );
    }


    @Test
    @Repeat( 3 )
    public void runTestInvocationInvalidQueries() {
        testEachInvalidStatement( "runTestInvocationInvalidQueries()", "sql/jsqltuner-test-invocation-invalid-queries.sql" );
    }

    @Test
    @Repeat( 2 )
    public void runTestParserInvalidQueries() {
        testEachInvalidStatement( "runTestParserInvalidQueries()", "sql/jsqltuner-test-parser-invalid-queries.sql" );
    }

}
