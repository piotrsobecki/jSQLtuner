package pl.piotrsukiennik.tuner.service.impl.transactional;

import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.persistance.QueryDao;
import pl.piotrsukiennik.tuner.service.QueryService;
import pl.piotrsukiennik.tuner.util.hash.HashGenerators;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
//@Service
@Transactional("jsqlTunerTransactionManager")
public class QueryServiceImpl implements QueryService {

    private Map<String, Query> cachedQueries = new LinkedHashMap<>();

    protected QueryDao getQueryDao() {
        return Dao.getQuery();
    }

    protected <T extends Query> T get( String hash ) {
        T query = (T) cachedQueries.get( hash );
        if ( query == null ) {
            query = getQueryDao().getQuery( hash );
            cachedQueries.put( hash, query );
        }
        return query;
    }


    @Override
    public <T extends Query> T get( String database, String schema, String queryStr ) throws QueryParsingNotSupportedException {
        String hash = getHash( database, schema, queryStr );
        return get( hash );
    }

    protected String getHash( String databaseStr, String schemaStr, String query ) {
        return HashGenerators.MD5.getHash( databaseStr + "." + schemaStr + "." + query );
    }

    @Override
    public <T extends Query> void save( T query, String database, String schema, String queryStr ) {
        save( query, getHash( database, schema, queryStr ) );
    }


    protected <T extends Query> void save( T query, String hash ) {
        query.setHash( hash );
        getQueryDao().createQuery( query );
        cachedQueries.put( hash, query );
    }

    @Override
    public void save( WriteQueryExecution writeQueryExecution ) {
        getQueryDao().createQueryExecution( writeQueryExecution );
    }
}
