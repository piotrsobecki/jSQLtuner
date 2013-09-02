package pl.piotrsukiennik.tuner.util.holder;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.service.ILogService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryExecutionService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryService;
import pl.piotrsukiennik.tuner.persistance.service.ISchemaService;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
@Component
public class ServicesHolder extends Holder {


    private ILogService logService;
    private IQueryService queryService;
    private ISchemaService schemaService;
    private IQueryExecutionService queryExecutionService;

    public ILogService getLogService() {
        if (logService==null){
            logService = applicationContext.getBean("LogService",ILogService.class);
        }
        return logService;
    }

    void setLogService(ILogService logService) {
        this.logService = logService;
    }

    public IQueryService getQueryService() {
        if (queryService==null){
            queryService = applicationContext.getBean("QueryService",IQueryService.class);
        }
        return queryService;
    }

    void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }

    public ISchemaService getSchemaService() {
        if (schemaService==null){
            schemaService = applicationContext.getBean("SchemaService",ISchemaService.class);
        }
        return schemaService;
    }

    void setSchemaService(ISchemaService schemaService) {
        this.schemaService = schemaService;
    }

    public IQueryExecutionService getQueryExecutionService() {
        if (queryExecutionService==null){
            queryExecutionService = applicationContext.getBean("QueryExecutionService",IQueryExecutionService.class);
        }
        return queryExecutionService;
    }

    public void setQueryExecutionService(IQueryExecutionService queryExecutionService) {
        this.queryExecutionService = queryExecutionService;
    }
}
