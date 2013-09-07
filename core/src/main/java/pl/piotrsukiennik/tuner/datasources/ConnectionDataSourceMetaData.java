package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.service.ILogService;
import pl.piotrsukiennik.tuner.persistance.service.transactional.LogServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 21:15
 */
public class ConnectionDataSourceMetaData implements IDataSourceMetaData {
    private final static String EXCEPTION_FORMAT = "%s.getIdentifier() Exception";

    private Connection connection;
    private String identifier;
    public ConnectionDataSourceMetaData(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getIdentifier() {
        if (identifier!=null){
            return identifier;
        }
        try{
            return identifier =  connection.getMetaData().getURL();
        }
        catch (SQLException s){
            return "";
        }
    }
}
