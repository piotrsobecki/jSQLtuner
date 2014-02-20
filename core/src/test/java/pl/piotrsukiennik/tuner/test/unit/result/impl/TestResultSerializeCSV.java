package pl.piotrsukiennik.tuner.test.unit.result.impl;

import pl.piotrsukiennik.tuner.test.unit.result.TestResult;
import pl.piotrsukiennik.tuner.test.unit.result.TestResultSerialize;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class TestResultSerializeCSV implements TestResultSerialize<String> {

    private static String DEFAUlT_SEPARATOR = ",";

    private String separator = DEFAUlT_SEPARATOR;

    @Override
    public String serialize( TestResult testResult ) {
        return testResult.getClazz().getSimpleName()
         + separator + testResult.getMethod()
         + separator + testResult.getTimeUnit().name()
         + separator + testResult.getDuration();
    }
}
