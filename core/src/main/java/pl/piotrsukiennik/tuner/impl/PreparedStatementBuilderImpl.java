package pl.piotrsukiennik.tuner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.ParsingQueryService;
import pl.piotrsukiennik.tuner.PreparedStatementBuilder;
import pl.piotrsukiennik.tuner.ShardService;
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
@Service( "pl.piotrsukiennik.tuner.service.PreparedStatementBuilder.impl" )
public class PreparedStatementBuilderImpl implements PreparedStatementBuilder, StatementBuilder {

    @Autowired
    private ParsingQueryService queryService;

    @Autowired
    private ShardService shardService;

    @Autowired
    private DataSourceContext dataSourceContext;

    @Override
    public Statement build( Statement statement ) {
        String database = Statements.getDatabase( statement );
        String schema = Statements.getSchema( statement );
        if ( !dataSourceContext.contains( database, schema ) ) {
            return new SExecutionIntercepting<>( statement, shardService, queryService, database, schema );
        }
        return statement;
    }

    @Override
    public PreparedStatement build( PreparedStatement preparedStatement, String sql ) {
        String database = Statements.getDatabase( preparedStatement );
        String schema = Statements.getSchema( preparedStatement );
        if ( !dataSourceContext.contains( database, schema ) ) {
            PSParametersIntercepting<PreparedStatement> parametersIntercepting = new PSParametersIntercepting<>( preparedStatement );
            return new PSExecutionIntercepting<>( parametersIntercepting, shardService, queryService, database, schema, sql );
        }
        return preparedStatement;
    }


}
