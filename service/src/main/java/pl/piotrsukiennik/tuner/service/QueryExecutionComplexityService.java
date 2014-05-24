package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.exception.ResultSetMetaDataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface QueryExecutionComplexityService {
    ReadQueryExecutionComplexityEstimation estimate(ReadQuery readQuery,CachedRowSet cachedRowSet,long executionTimeNano) throws ResultSetMetaDataRetrievalException;
}
