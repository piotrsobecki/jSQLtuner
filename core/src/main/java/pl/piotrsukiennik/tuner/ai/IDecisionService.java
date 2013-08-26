package pl.piotrsukiennik.tuner.ai;

import pl.piotrsukiennik.tuner.parser.IQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:13
 */
public interface IDecisionService {
    boolean checkToProxy(String schema);
    boolean checkProceed(IQuery query);
    PreparedStatement proceed(PreparedStatement source, Connection connection,String query) throws Throwable;

}
