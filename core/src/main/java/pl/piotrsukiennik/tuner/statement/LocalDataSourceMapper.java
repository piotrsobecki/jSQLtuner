package pl.piotrsukiennik.tuner.statement;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 10:18
 */
@Component
public class LocalDataSourceMapper {

    private @Resource List<IDataSource> dataSourceList;

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

    public Collection<IDataSource> getLocal(DataSource dataSource){
        if (dataSource==null){
            return Collections.EMPTY_LIST;
        }
        return Collections2.filter(dataSourceList, new IdentifierPredicate(dataSource.getIdentifier()));
    }
}
