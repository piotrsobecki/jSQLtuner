package pl.piotrsukiennik.tuner.test.unit;

import junit.framework.TestCase;
import pl.piotrsukiennik.tuner.test.unit.result.TestResult;
import pl.piotrsukiennik.tuner.test.unit.result.TestResultsSerialize;
import pl.piotrsukiennik.tuner.test.unit.result.impl.TestResultImpl;
import pl.piotrsukiennik.tuner.util.TimedCallable;
import pl.piotrsukiennik.tuner.util.TimedCallableImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

    private static Collection<TestResult> testResults = new ArrayList<>();

    public <V> void test( String testMethod, Callable<V> callable ) {
        try {
            TimedCallable<V> timedCallable = new TimedCallableImpl<V>( callable );
            timedCallable.call();
            long millis = timedCallable.getTime( TimeUnit.MILLISECONDS );
            testResults.add( new TestResultImpl( getClass(), testMethod, TimeUnit.MILLISECONDS, millis ) );
            System.out.println( String.format( MESSAGE_FORMAT, getClass().getSimpleName(), testMethod, millis ) );
        }
        catch ( Exception e ) {
            e.printStackTrace();
            TestCase.fail();
        }
    }

    public static <O extends Serializable> O accept( TestResultsSerialize<O> testResultsSerialize ) {
        return testResultsSerialize.serialize( testResults );
    }

    public static void beforeClassMethod() {
        testResults.clear();
    }


}
