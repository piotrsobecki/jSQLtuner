package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryService;
import pl.piotrsukiennik.tuner.util.hash.HashGenerators;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@Service
public class BlankQueryServiceImpl implements QueryService {

    private Map<String, Query> cachedQueries = new LinkedHashMap<>();


    protected <T extends Query> T get( String hash ) {
        return (T) cachedQueries.get( hash );
    }


    @Override
    public <T extends Query> T getQuery( String database, String schema, String queryStr ) throws QueryParsingNotSupportedException {
        String hash = getHash( database, schema, queryStr );
        return get( hash );
    }

    protected String getHash( String databaseStr, String schemaStr, String query ) {
        return HashGenerators.MD5.getHash( databaseStr + "." + schemaStr + "." + query );
    }

    @Override
    public <T extends Query> void createQuery( T query, String database, String schema, String queryStr ) {
        save( query, getHash( database, schema, queryStr ) );
    }


    protected <T extends Query> void save( T query, String hash ) {
        query.setHash( hash );
        cachedQueries.put( hash, query );
    }

    @Override
    public void createExecution( WriteQueryExecution writeQueryExecution ) {
    }
}
