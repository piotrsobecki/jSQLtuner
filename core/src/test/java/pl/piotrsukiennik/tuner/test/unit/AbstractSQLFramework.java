package pl.piotrsukiennik.tuner.test.unit;

import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
import pl.piotrsukiennik.tuner.test.util.Statements;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */

public abstract class AbstractSQLFramework extends AbstractFrameworkCommon {

    @Autowired
    protected SQLQueryExecutionService queryExecutionService;


    public void testEachValidStatement( String method, final String sqlFile ) {
        test( method, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Statements.forEachValid( getClass().getClassLoader(), sqlFile, new Statements.StringProcessor() {
                    @Override
                    public void call( String line ) {
                        try {
                            List l = queryExecutionService.execute( line );
                            System.out.println( l );
                        }
                        catch ( Exception e ) {
                            e.printStackTrace();
                            TestCase.fail();
                        }
                    }
                } );
                return true;
            }
        } );
    }

    public void testEachInvalidStatement( String method, final String sqlFile ) {
        test( method, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
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
                return true;
            }
        } );
    }

}
