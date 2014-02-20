package pl.piotrsukiennik.tuner.test.unit.suite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.annotation.Repeat;
import pl.piotrsukiennik.tuner.test.unit.AbstractSQLFramework;
import pl.piotrsukiennik.tuner.test.unit.query.SqlTest;
import pl.piotrsukiennik.tuner.test.unit.query.SqlTestJsqlTunerAop;
//import pl.piotrsukiennik.tuner.test.unit.query.SqlTestJsqlTunerWrapper;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
 SqlTest.class,
 SqlTestJsqlTunerAop.class,
 // SqlTestJsqlTunerWrapper.class

})
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
