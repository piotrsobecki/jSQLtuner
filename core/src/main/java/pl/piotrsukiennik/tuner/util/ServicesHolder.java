package pl.piotrsukiennik.tuner.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.service.ILogService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryService;
import pl.piotrsukiennik.tuner.persistance.service.ISchemaService;
import pl.piotrsukiennik.tuner.persistance.service.transactional.LogServiceImpl;
import pl.piotrsukiennik.tuner.persistance.service.transactional.QueryServiceImpl;
import pl.piotrsukiennik.tuner.persistance.service.transactional.SchemaServiceImpl;

import javax.annotation.Resource;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
@Component
public class ServicesHolder {

    private @Resource
    ApplicationContext applicationContext;


    private ILogService logService;
    private IQueryService queryService;
    private ISchemaService schemaService;


    ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
}
