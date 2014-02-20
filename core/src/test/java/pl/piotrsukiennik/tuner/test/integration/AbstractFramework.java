package pl.piotrsukiennik.tuner.test.integration;

import junit.framework.TestCase;
import pl.piotrsukiennik.tuner.util.TimedCallable;
import pl.piotrsukiennik.tuner.util.TimedCallableImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Basic template
 *
 * @author Piotr Sukiennik
 * @Test public void test1() {
 * test( "test1()", new Callable<Object>() {
 * @Override public Object call() throws Exception {
 * return null;
 * }
 * });
 * }
 * @date 19.02.14
 */
public abstract class AbstractFramework {

    public static final String MESSAGE_FORMAT = "Execution of (%s.%s) took %d millis.";

    public <V> void test( String testMethod, Callable<V> callable ) {
        try {
            TimedCallable<V> timedCallable = new TimedCallableImpl<V>( callable );
            timedCallable.call();
            long millis = timedCallable.getTime( TimeUnit.MILLISECONDS );
            System.out.println( String.format( MESSAGE_FORMAT, getClass().getSimpleName(), testMethod, millis ) );
        }
        catch ( Exception e ) {
            e.printStackTrace();
            TestCase.fail();
        }

    }
}
