package pl.piotrsukiennik.tuner.test.unit;

import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
import pl.piotrsukiennik.tuner.test.util.Utils;

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
                Utils.processEachLine( getClass().getClassLoader(), sqlFile, new Utils.StringProcessor() {
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
                return true;
            }
        } );
    }

    public void testEachInvalidStatement( String method, final String sqlFile ) {
        test( method, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Utils.processEachLine( getClass().getClassLoader(), sqlFile, new Utils.StringProcessor() {
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
                return true;
            }
        } );
    }

}
