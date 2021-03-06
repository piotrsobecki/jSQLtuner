package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryProviderService;
import pl.piotrsukiennik.tuner.service.QueryService;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 01.03.14
 */
@Service("queryProviderService")
public class QueryProviderServiceImpl implements QueryProviderService {

    @Resource(name = "parsingQueryProviderService")
    private QueryProviderService queryProviderService;

    @Resource
    private QueryService queryService;

    @Override
    public <T extends Query> T provide( String database, String schema, String query ) throws QueryParsingNotSupportedException {
        T queryParsed = queryService.getQuery( database, schema, query );
        if ( queryParsed == null ) {
            queryParsed = queryProviderService.provide( database, schema, query );
            queryService.createQuery( queryParsed, database, schema, query );
        }
        return queryParsed;
    }
}
