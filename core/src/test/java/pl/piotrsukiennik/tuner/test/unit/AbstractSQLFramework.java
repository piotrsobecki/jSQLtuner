package pl.piotrsukiennik.tuner.test.unit;

import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
import pl.piotrsukiennik.tuner.test.util.Statements;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */

public abstract class AbstractSQLFramework {

    @Autowired
    protected SQLQueryExecutionService queryExecutionService;


    public void testEachValidStatement( final String sqlFile ) {
        Statements.forEachValid( getClass().getClassLoader(), sqlFile, new Statements.StringProcessor() {
            @Override
            public void call( String line ) {
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

    public void testEachInvalidStatement( final String sqlFile ) {
        Statements.forEachInValid( getClass().getClassLoader(), sqlFile, new Statements.StringProcessor() {
            @Override
            public void call( String line ) {
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
