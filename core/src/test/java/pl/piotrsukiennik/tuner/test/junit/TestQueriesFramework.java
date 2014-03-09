package pl.piotrsukiennik.tuner.test.junit;

import junit.framework.TestCase;
import org.hibernate.HibernateException;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
import pl.piotrsukiennik.tuner.test.util.Statements;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */

public abstract class TestQueriesFramework {

    public void testEachValidStatement( final SQLQueryExecutionService queryExecutionService, final String sqlFile ) {
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

    public void testEachInvalidStatement( final SQLQueryExecutionService queryExecutionService, final String sqlFile ) {
        Statements.forEachInValid( getClass().getClassLoader(), sqlFile, new Statements.StringProcessor() {
            @Override
            public void call( String line ) {
                try {
                    queryExecutionService.execute( line );
                    TestCase.fail();
                }
                catch ( HibernateException he ) {
                    if ( he.getLocalizedMessage().startsWith( "No Session" ) ) {
                        TestCase.fail();
                    }
                    he.printStackTrace();
                }
            }
        } );
    }

}
