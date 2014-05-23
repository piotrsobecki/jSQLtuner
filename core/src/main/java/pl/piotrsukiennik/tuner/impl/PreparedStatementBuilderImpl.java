package pl.piotrsukiennik.tuner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.CompositeDataSource;
import pl.piotrsukiennik.tuner.ParsingQueryService;
import pl.piotrsukiennik.tuner.PreparedStatementBuilder;
import pl.piotrsukiennik.tuner.StatementBuilder;
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
@Component("preparedStatementBuilder")
public class PreparedStatementBuilderImpl implements PreparedStatementBuilder, StatementBuilder {

    private ParsingQueryService queryService;

    private CompositeDataSource compositeDataSource;

    private DataSourceContext dataSourceContext;

    @Autowired
    public PreparedStatementBuilderImpl(
        ParsingQueryService queryService,
        CompositeDataSource compositeDataSource,
        DataSourceContext dataSourceContext
    ) {
        this.queryService = queryService;
        this.compositeDataSource = compositeDataSource;
        this.dataSourceContext = dataSourceContext;
    }

    @Override
    public Statement build( Statement statement ) {
        String database = Statements.getDatabase( statement );
        String schema = Statements.getSchema( statement );
        if ( !dataSourceContext.contains( database, schema ) ) {
            return new SExecutionIntercepting<>( statement, compositeDataSource, queryService, database, schema );
        }
        return statement;
    }

    @Override
    public PreparedStatement build( PreparedStatement preparedStatement, String sql ) {
        String database = Statements.getDatabase( preparedStatement );
        String schema = Statements.getSchema( preparedStatement );
        if ( !dataSourceContext.contains( database, schema ) ) {
            PSParametersIntercepting<PreparedStatement> parametersIntercepting = new PSParametersIntercepting<>( preparedStatement );
            return new PSExecutionIntercepting<>( parametersIntercepting, compositeDataSource, queryService, database, schema, sql );
        }
        return preparedStatement;
    }


}
