package pl.piotrsukiennik.tuner.servicepersistent.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.persistence.Dao;
import pl.piotrsukiennik.tuner.persistence.QueryDao;
import pl.piotrsukiennik.tuner.service.QueryService;
import pl.piotrsukiennik.tuner.util.hash.HashGenerators;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@Service
public class QueryServiceImpl implements QueryService {

    private Map<String, Query> cachedQueries = new LinkedHashMap<>();

    protected QueryDao getQueryDao() {
        return Dao.getQuery();
    }

    public <T extends Query> T getQuery( String hash ) {
        T query = (T) cachedQueries.get( hash );
        if ( query == null ) {
            query = getQueryDao().getQuery( hash );
            cachedQueries.put( hash, query );
        }
        return query;
    }


    @Override
    public <T extends Query> T getQuery( String database, String schema, String queryStr ) throws QueryParsingNotSupportedException {
        String hash = getHash( database, schema, queryStr );
        return getQuery( hash );
    }

    protected String getHash( String databaseStr, String schemaStr, String query ) {
        return HashGenerators.MD5.getHash( databaseStr + "." + schemaStr + "." + query );
    }

    @Override
    public <T extends Query> void createQuery( T query, String database, String schema, String queryStr ) {
        createQuery( query, getHash( database, schema, queryStr ) );
    }


    protected <T extends Query> void createQuery( T query, String hash ) {
        query.setHash( hash );
        getQueryDao().createQuery( query );
        cachedQueries.put( hash, query );
    }

    @Override
    public void createExecution( WriteQueryExecution writeQueryExecution ) {
        getQueryDao().createQueryExecution( writeQueryExecution );
    }
}
