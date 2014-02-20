package pl.piotrsukiennik.tuner.test.unit.result;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public interface TestResultsSerialize<O extends Serializable> {
    O serialize( Collection<TestResult> testResult );
}
