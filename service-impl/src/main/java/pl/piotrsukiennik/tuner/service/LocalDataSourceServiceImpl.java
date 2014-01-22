package pl.piotrsukiennik.tuner.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.execution.DataSource;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 10:18
 */
@Service
class LocalDataSourceServiceImpl implements LocalDataSourceService {

    private
    @Resource
    Set<IDataSource> dataSourceList;

    private Map<String, IDataSource> rootDataSources = new LinkedHashMap<String, IDataSource>();

    private class IdentifierPredicate implements Predicate<IDataSource> {
        private String identifier;

        private IdentifierPredicate( String identifier ) {
            this.identifier = identifier;
        }

        @Override
        public boolean apply( IDataSource iDataSource ) {
            return identifier.equalsIgnoreCase( iDataSource.getMetaData().getIdentifier() );
        }
    }

    @Override
    public IDataSource getRootDataSource( Query selectQuery ) {
        return rootDataSources.get( selectQuery.getHash() );
    }

    @Override
    public void setRootDataSource( Query query, IDataSource dataSource ) {
        rootDataSources.put( query.getHash(), dataSource );
    }

    @Override
    public Collection<IDataSource> getLocal( Query query, DataSource dataSource ) {
        if ( dataSource == null ) {
            return Collections.EMPTY_LIST;
        }
        IdentifierPredicate identifierPredicate = new IdentifierPredicate( dataSource.getIdentifier() );
        Collection<IDataSource> out = new LinkedHashSet<IDataSource>( Collections2.filter( dataSourceList, identifierPredicate ) );
        IDataSource rootDS = getRootDataSource( query );
        if ( identifierPredicate.apply( rootDS ) ) {
            out.add( rootDS );
        }
        return out;
    }
}
