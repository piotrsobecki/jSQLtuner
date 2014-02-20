package pl.piotrsukiennik.tuner.test.unit.result.impl;

import pl.piotrsukiennik.tuner.test.unit.result.TestResult;
import pl.piotrsukiennik.tuner.test.unit.result.TestResultSerialize;
import pl.piotrsukiennik.tuner.test.unit.result.TestResultsSerialize;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class TestResultsSerializeCSV implements TestResultsSerialize<String> {

    private TestResultSerialize<String> resultSerialize = new TestResultSerializeCSV();

    private static String DEFAUlT_SEPARATOR = "\n";

    private String separator = DEFAUlT_SEPARATOR;

    @Override
    public String serialize( Collection<TestResult> testResult ) {
        String out = "";
        String comma = "";
        for ( TestResult result : testResult ) {
            out += comma + result.accept( resultSerialize );
            comma = separator;
        }
        return out;

    }
}
