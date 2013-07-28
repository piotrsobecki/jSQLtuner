package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.ai.PreparedStatementWrapper;

import java.sql.PreparedStatement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:32
 */
public class ProceedWrapper implements PreparedStatementWrapper {
    private MethodInvocation methodInvocation;

    public ProceedWrapper(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public PreparedStatement get() throws Throwable {
        return (PreparedStatement)methodInvocation.proceed();
    }
}
