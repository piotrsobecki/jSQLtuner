package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.execution.DataSource;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public interface LocalDataSourceService {
    IDataSource getRootDataSource( Query selectQuery );

    void setRootDataSource( Query query, IDataSource dataSource );

    Collection<IDataSource> getLocal( Query query, DataSource dataSource );
}
