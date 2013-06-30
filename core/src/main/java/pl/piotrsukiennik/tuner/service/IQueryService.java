package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.execution.QueryExecution;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:30
 */
public interface IQueryService {
    void submit(QueryExecution queryExecution);
}
