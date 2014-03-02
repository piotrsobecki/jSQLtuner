package pl.piotrsukiennik.tuner.test.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import pl.piotrsukiennik.tuner.test.unit.query.SqlTestJsqlTunerWrapper;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public abstract class TestQueries extends AbstractSQLFramework {

    private static String TEST_TABLE_CREATE = "CREATE TABLE TestTable (id int,key varchar (255),value varchar (255))";

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
    public void runTestValidQueries() {
        testEachValidStatement( "sql/jsqltuner-test-valid-queries.sql" );
    }


    @Test
    public void runTestInvocationInvalidQueries() {
        testEachInvalidStatement( "sql/jsqltuner-test-invocation-invalid-queries.sql" );
    }

    @Test
    public void runTestParserInvalidQueries() {
        testEachInvalidStatement( "sql/jsqltuner-test-parser-invalid-queries.sql" );
    }

}
