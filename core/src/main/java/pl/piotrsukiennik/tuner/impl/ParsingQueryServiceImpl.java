package pl.piotrsukiennik.tuner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.Parser;
import pl.piotrsukiennik.tuner.ParsingQueryService;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryService;

/**
 * @author Piotr Sukiennik
 * @date 01.03.14
 */
@Service
public class ParsingQueryServiceImpl implements ParsingQueryService {

    @Autowired
    private Parser parser;

    @Autowired
    private QueryService queryService;


    @Override
    public <T extends Query> T get( String database, String schema, String query ) throws QueryParsingNotSupportedException {
        T queryParsed = queryService.getQuery( database, schema, query );
        if ( queryParsed == null ) {
            queryParsed = parser.parse( database, schema, query );
            queryService.createQuery( queryParsed, database, schema, query );
        }
        return queryParsed;
    }


}
