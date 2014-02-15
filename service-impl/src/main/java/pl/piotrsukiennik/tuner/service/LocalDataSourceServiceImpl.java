package pl.piotrsukiennik.tuner.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 10:18
 */
@Service
class LocalDataSourceServiceImpl implements LocalDataSourceService {

    @Resource
    private Set<DataSource> dataSourceList;

    private Map<String, DataSource> rootDataSources = new LinkedHashMap<String, DataSource>();

    private class IdentifierPredicate implements Predicate<DataSource> {
        private String identifier;

        private IdentifierPredicate( String identifier ) {
            this.identifier = identifier;
        }

        @Override
        public boolean apply( DataSource dataSource ) {
            return identifier.equalsIgnoreCase( dataSource.getDataSourceIdentity().getIdentifier() );
        }
    }

    @Override
    public DataSource getRootDataSource( Query selectQuery ) {
        return rootDataSources.get( selectQuery.getHash() );
    }

    @Override
    public void setRootDataSource( Query query, DataSource dataSource ) {
        rootDataSources.put( query.getHash(), dataSource );
    }

    @Override
    public Collection<DataSource> getLocal( Query query, DataSourceIdentity dataSourceIdentity ) {
        if ( dataSourceIdentity == null ) {
            return Collections.EMPTY_LIST;
        }
        IdentifierPredicate identifierPredicate = new IdentifierPredicate( dataSourceIdentity.getIdentifier() );
        Collection<DataSource> out = new LinkedHashSet<DataSource>( Collections2.filter( dataSourceList, identifierPredicate ) );
        DataSource rootDS = getRootDataSource( query );
        if ( identifierPredicate.apply( rootDS ) ) {
            out.add( rootDS );
        }
        return out;
    }

    @Override
    public DataSource getSingleLocal( Query query, DataSourceIdentity dataSourceIdentity ) {
        Collection<DataSource> local = getLocal( query, dataSourceIdentity );
        Iterator<DataSource> iterator = local.iterator();
        if ( iterator.hasNext() ) {
            DataSource dataSourceLocal = iterator.next();
            dataSourceLocal.setDataSourceIdentity( dataSourceIdentity );
            return dataSourceLocal;
        }
        return null;
    }
}
