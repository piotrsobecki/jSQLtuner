package pl.piotrsukiennik.tuner.test.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
//import pl.piotrsukiennik.tuner.test.benchmark.query.SqlTestJsqlTunerWrapper;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public abstract class TestQueries extends TestQueriesFramework {

    @Autowired
    protected SQLQueryExecutionService queryExecutionService;

    @Test
    public void runTestValidQueries() {
        testEachValidStatement( queryExecutionService, "test-sql/jsqltuner-test-valid-queries.sql" );
    }


    @Test
    public void runTestInvocationInvalidQueries() {
        testEachInvalidStatement( queryExecutionService, "test-sql/jsqltuner-test-invocation-invalid-queries.sql" );
    }

    @Test
    public void runTestParserInvalidQueries() {
        testEachInvalidStatement( queryExecutionService, "test-sql/jsqltuner-test-parser-invalid-queries.sql" );
    }

}
