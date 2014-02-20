package pl.piotrsukiennik.tuner.test.unit.result;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public interface TestResult {
    Class getClazz();

    String getMethod();

    TimeUnit getTimeUnit();

    long getDuration();

    <O extends Serializable> O accept( TestResultSerialize<O> serializer );
}
