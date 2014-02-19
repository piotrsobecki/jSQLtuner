package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.aop.wrapper.PSExecutionIntercepting;
import pl.piotrsukiennik.tuner.aop.wrapper.PSParametersIntercepting;
import pl.piotrsukiennik.tuner.aop.wrapper.SExecutionIntercepting;
import pl.piotrsukiennik.tuner.service.DataSourceContext;
import pl.piotrsukiennik.tuner.service.PreparedStatementBuilder;
import pl.piotrsukiennik.tuner.service.QueryService;
import pl.piotrsukiennik.tuner.util.Statements;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
@Service
public class PreparedStatementBuilderImpl implements PreparedStatementBuilder {

    @Autowired
    private QueryService queryService;

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
