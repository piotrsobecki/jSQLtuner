package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.model.query.IQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:13
 */
public interface QueryProxyService {
    boolean checkToProxy( String schema );

    boolean checkProceed( IQuery query );

    PreparedStatement proceed( PreparedStatement source, Connection connection, String query );

}
