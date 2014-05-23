package pl.piotrsukiennik.tuner.statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.CompositeDataSource;
import pl.piotrsukiennik.tuner.QueryProviderService;
import pl.piotrsukiennik.tuner.service.DataSourceContext;
import pl.piotrsukiennik.tuner.statement.impl.PSExecutionIntercepting;
import pl.piotrsukiennik.tuner.statement.impl.PSParametersIntercepting;
import pl.piotrsukiennik.tuner.statement.impl.SExecutionIntercepting;
import pl.piotrsukiennik.tuner.statement.impl.Statements;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class PreparedStatementBuilderImpl implements PreparedStatementBuilder, StatementBuilder {

    private QueryProviderService queryProviderService;

    private CompositeDataSource compositeDataSource;

    private DataSourceContext dataSourceContext;

    @Autowired
    public PreparedStatementBuilderImpl(
        @Qualifier("queryProviderService") QueryProviderService queryProviderService,
        CompositeDataSource compositeDataSource,
        DataSourceContext dataSourceContext
    ) {
        this.queryProviderService = queryProviderService;
        this.compositeDataSource = compositeDataSource;
        this.dataSourceContext = dataSourceContext;
    }

    @Override
    public Statement build( Statement statement ) {
        String database = Statements.getDatabase( statement );
        String schema = Statements.getSchema( statement );
        if ( !dataSourceContext.contains( database, schema ) ) {
            return new SExecutionIntercepting<>( statement, compositeDataSource, queryProviderService, database, schema );
        }
        return statement;
    }

    @Override
    public PreparedStatement build( PreparedStatement preparedStatement, String sql ) {
        String database = Statements.getDatabase( preparedStatement );
        String schema = Statements.getSchema( preparedStatement );
        if ( !dataSourceContext.contains( database, schema ) ) {
            PSParametersIntercepting<PreparedStatement> parametersIntercepting = new PSParametersIntercepting<>( preparedStatement );
            return new PSExecutionIntercepting<>( parametersIntercepting, compositeDataSource, queryProviderService, database, schema, sql );
        }
        return preparedStatement;
    }


}
