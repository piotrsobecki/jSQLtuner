package pl.piotrsukiennik.tuner.service.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.QueryParserService;
import pl.piotrsukiennik.tuner.service.parser.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.StatementParserVisitor;
import pl.piotrsukiennik.tuner.service.util.hash.HashGenerators;

import javax.annotation.Resource;
import java.io.StringReader;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 19:05
 */
@Service
@Transactional(value = "jsqlTunerTransactionManager")
class QueryParserServiceImpl implements QueryParserService {

    private static final Log LOG = LogFactory.getLog( QueryParserService.class );

    @Resource
    private QueryElementParserService queryElementParserService;


    protected String getQueryHash( String databaseStr, String schemaStr, String query ) {
        return HashGenerators.MD5.getHash( databaseStr + "." + schemaStr + "." + query );
    }

    @Override
    public Query parse( String databaseStr, String schemaStr, String query ) throws QueryParsingNotSupportedException {
        try {
            CCJSqlParserManager pm = new CCJSqlParserManager();
            QueryContext queryContext = new QueryContextImpl();
            queryContext.getDatabase( databaseStr );
            queryContext.getSchema( schemaStr );
            String queryHash = getQueryHash( databaseStr, schemaStr, query );
            Query parsedQuery = DaoHolder.getQueryDao().getQueryByHash( queryHash );
            if ( parsedQuery == null ) {
                Statement statement = pm.parse( new StringReader( query ) );
                StatementParserVisitor statementParserVisitor = new StatementParserVisitor( queryElementParserService, queryContext, statement );
                parsedQuery = statementParserVisitor.getQuery();
                if ( parsedQuery != null ) {
                    parsedQuery.setHash( queryHash );
                    DaoHolder.getQueryDao().submit( parsedQuery );
                }
                else {
                    DaoHolder.getLogDao().logException( query, "Query parsing not supported!" );
                    throw new QueryParsingNotSupportedException( query );
                }
            }
            return parsedQuery;
        }
        catch ( JSQLParserException e ) {
            DaoHolder.getLogDao().logException( query, e );
            throw new QueryParsingNotSupportedException( query, e );
        }
    }

}
