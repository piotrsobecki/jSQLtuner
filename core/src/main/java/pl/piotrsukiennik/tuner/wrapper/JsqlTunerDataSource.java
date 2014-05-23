package pl.piotrsukiennik.tuner.wrapper;

import pl.piotrsukiennik.tuner.statement.PreparedStatementBuilder;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class JsqlTunerDataSource extends DataSourceWrapper {

    private PreparedStatementBuilder statementBuilder;


    public PreparedStatementBuilder getStatementBuilder() {
        return statementBuilder;
    }

    public void setStatementBuilder( PreparedStatementBuilder statementBuilder ) {
        this.statementBuilder = statementBuilder;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new JsqlTunerConnection( super.getConnection(), statementBuilder );
    }

    @Override
    public Connection getConnection( String username, String password ) throws SQLException {
        return new JsqlTunerConnection( super.getConnection( username, password ), statementBuilder );
    }


}
