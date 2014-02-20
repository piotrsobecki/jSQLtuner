package pl.piotrsukiennik.tuner.test.unit.result.impl;

import pl.piotrsukiennik.tuner.test.unit.result.TestResult;
import pl.piotrsukiennik.tuner.test.unit.result.TestResultSerialize;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class TestResultImpl implements TestResult {
    private Class clazz;

    private String method;

    private TimeUnit timeUnit;

    private long duration;


    public TestResultImpl( Class clazz, String method, TimeUnit timeUnit, long duration ) {
        this.clazz = clazz;
        this.method = method;
        this.timeUnit = timeUnit;
        this.duration = duration;
    }

    @Override
    public Class getClazz() {
        return clazz;
    }

    public void setClazz( Class clazz ) {
        this.clazz = clazz;
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod( String method ) {
        this.method = method;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit( TimeUnit timeUnit ) {
        this.timeUnit = timeUnit;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    public void setDuration( long duration ) {
        this.duration = duration;
    }

    @Override
    public <O extends Serializable> O accept( TestResultSerialize<O> serializer ) {
        return serializer.serialize( this );
    }
}
