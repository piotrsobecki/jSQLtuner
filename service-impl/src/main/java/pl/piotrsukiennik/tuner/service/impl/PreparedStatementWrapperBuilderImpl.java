package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.Sharder;
import pl.piotrsukiennik.tuner.service.DataSourceContext;
import pl.piotrsukiennik.tuner.service.PreparedStatementWrapperBuilder;
import pl.piotrsukiennik.tuner.service.QueryParserService;
import pl.piotrsukiennik.tuner.service.statement.wrapper.PSExecutionIntercepting;
import pl.piotrsukiennik.tuner.service.statement.wrapper.PSParametersIntercepting;
import pl.piotrsukiennik.tuner.service.util.Statements;

import java.sql.PreparedStatement;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
@Service
public class PreparedStatementWrapperBuilderImpl implements PreparedStatementWrapperBuilder {

    @Autowired
    private
    QueryParserService parserService;

    @Autowired
    private
    Sharder sharder;

    @Autowired
    private DataSourceContext dataSourceContext;

    @Override
    public PreparedStatement build( PreparedStatement preparedStatement, String sql ) {
        String database = Statements.getDatabase( preparedStatement );
        String schema = Statements.getSchema( preparedStatement );
        if ( !dataSourceContext.isPartOf( database, schema ) ) {
            PSParametersIntercepting<PreparedStatement> parametersIntercepting = new PSParametersIntercepting<>( preparedStatement );
            return new PSExecutionIntercepting<>( parametersIntercepting, sharder, parserService, database, schema, sql );
        }
        return preparedStatement;
    }


}
