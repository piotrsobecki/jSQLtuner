package pl.piotrsukiennik.tuner.test.unit.suite;

import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import pl.piotrsukiennik.tuner.test.unit.AbstractSQLFramework;
import pl.piotrsukiennik.tuner.test.unit.query.SqlTest;
import pl.piotrsukiennik.tuner.test.unit.query.SqlTestJsqlTunerAop;
import pl.piotrsukiennik.tuner.test.unit.query.SqlTestJsqlTunerWrapper;
//import pl.piotrsukiennik.tuner.test.unit.query.SqlTestJsqlTunerWrapper;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
 SqlTest.class,
 SqlTestJsqlTunerAop.class,
 SqlTestJsqlTunerWrapper.class
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AxisRange( min = 0, max = 1 )
@BenchmarkMethodChart
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
