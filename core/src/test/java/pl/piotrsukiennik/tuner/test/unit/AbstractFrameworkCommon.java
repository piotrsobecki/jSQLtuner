package pl.piotrsukiennik.tuner.test.unit;

import pl.piotrsukiennik.tuner.test.unit.result.TestResultsSerialize;
import pl.piotrsukiennik.tuner.test.unit.result.impl.TestResultsSerializeCSV;


public abstract class AbstractFrameworkCommon extends AbstractFramework {

    private static TestResultsSerialize<String> testResultsSerialize = new TestResultsSerializeCSV();


    public static void afterClassMethod() {
        System.out.println( "============================" );
        System.out.println( "============================" );
        System.out.println( accept( testResultsSerialize ) );
        System.out.println( "============================" );
        System.out.println( "============================" );
    }


}
