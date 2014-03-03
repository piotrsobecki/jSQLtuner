package pl.piotrsukiennik.tuner.test.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
//import pl.piotrsukiennik.tuner.test.benchmark.query.SqlTestJsqlTunerWrapper;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        testEachValidStatement( "test-sql/jsqltuner-test-valid-queries.sql" );
    }


    @Test
    public void runTestInvocationInvalidQueries() {
        testEachInvalidStatement( "test-sql/jsqltuner-test-invocation-invalid-queries.sql" );
    }

    @Test
    public void runTestParserInvalidQueries() {
        testEachInvalidStatement( "test-sql/jsqltuner-test-parser-invalid-queries.sql" );
    }

}
