package pl.piotrsukiennik.tuner.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:16
 */
public class Statements {
    private Statements(){}

    public static String getSchema(Connection connection){
        try {
            return connection.getCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSchema(PreparedStatement preparedStatement){
        try {
            return getSchema(preparedStatement.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
