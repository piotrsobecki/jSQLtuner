package pl.piotrsukiennik.tuner.test.unit.result;

import java.io.Serializable;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public interface TestResultSerialize<O extends Serializable> {
    O serialize( TestResult testResult );
}
