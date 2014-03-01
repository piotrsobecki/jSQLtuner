package pl.piotrsukiennik.tuner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.parser.Parser;
import pl.piotrsukiennik.tuner.QueryProvider;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryService;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
@Service
public class QueryProviderImpl implements QueryProvider {

    @Autowired
    private Parser parser;

    @Autowired
    private QueryService queryService;

    @Override
    public <T extends Query> T getQuery( String database, String schema, String query ) throws QueryParsingNotSupportedException {
        T query1 = queryService.get( database, schema, query );
        if ( query1 == null ) {
            query1 = parser.parse( database, schema, query );
        }
        return query1;
    }
}
