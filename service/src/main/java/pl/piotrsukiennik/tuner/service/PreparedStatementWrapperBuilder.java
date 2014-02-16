package pl.piotrsukiennik.tuner.service;

import java.sql.PreparedStatement;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface PreparedStatementWrapperBuilder {
    PreparedStatement build( PreparedStatement preparedStatement, String sql );
}
