package pl.piotrsukiennik.tuner.statement;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 10:18
 */
@Component
public class LocalDataSourceMapper {

    private @Resource Set<IDataSource> dataSourceList;
    private Map<String,IDataSource> rootDataSources = new LinkedHashMap<String,IDataSource>();

    private class IdentifierPredicate implements Predicate<IDataSource>{
        private String identifier;

        private IdentifierPredicate(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public boolean apply(pl.piotrsukiennik.tuner.datasources.IDataSource iDataSource) {
            return identifier.equalsIgnoreCase(iDataSource.getMetaData().getIdentifier());
        }
    }

    public IDataSource getRootDataSource(Query selectQuery){
        return rootDataSources.get(selectQuery.getHash());
    }
    public void setRootDataSource(Query query, IDataSource dataSource){
        rootDataSources.put(query.getHash(), dataSource);
    }
    public Collection<IDataSource> getLocal(Query query,DataSource dataSource){
        if (dataSource==null){
            return Collections.EMPTY_LIST;
        }
        IdentifierPredicate identifierPredicate  = new IdentifierPredicate(dataSource.getIdentifier());
        Collection<IDataSource> out = new LinkedHashSet<IDataSource>(Collections2.filter(dataSourceList,identifierPredicate));
        IDataSource rootDS =  getRootDataSource(query);
        if (identifierPredicate.apply(rootDS)){
            out.add(rootDS);
        }
        return out;
    }
}
