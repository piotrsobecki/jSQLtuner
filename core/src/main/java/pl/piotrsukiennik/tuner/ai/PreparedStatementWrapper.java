package pl.piotrsukiennik.tuner.ai;

import java.sql.PreparedStatement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:24
 */
public interface PreparedStatementWrapper {
    PreparedStatement get() throws Throwable;
}
