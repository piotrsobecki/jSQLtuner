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
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.ParserService;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.StatementParserVisitor;
import pl.piotrsukiennik.tuner.util.hash.HashGenerators;

import javax.annotation.Resource;
import java.io.StringReader;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 19:05
 */
@Service
@Transactional(value = "jsqlTunerTransactionManager")
class ParserServiceImpl implements ParserService {

    private static final Log LOG = LogFactory.getLog( ParserService.class );

    @Resource
    private ElementParserService elementParserService;


    protected String getQueryHash( String databaseStr, String schemaStr, String query ) {
        return HashGenerators.MD5.getHash( databaseStr + "." + schemaStr + "." + query );
    }

    @Override
    public <T extends Query> T parse( String databaseStr, String schemaStr, String query ) throws QueryParsingNotSupportedException {
        try {
            CCJSqlParserManager pm = new CCJSqlParserManager();
            QueryContext queryContext = new QueryContextImpl();
            queryContext.getDatabase( databaseStr );
            queryContext.getSchema( schemaStr );
            String queryHash = getQueryHash( databaseStr, schemaStr, query );
            T parsedQuery = Dao.getQueryDao().getQueryByHash( queryHash );
            if ( parsedQuery == null ) {
                Statement statement = pm.parse( new StringReader( query ) );
                StatementParserVisitor<T> statementParserVisitor = new StatementParserVisitor<T>( elementParserService, queryContext, statement );
                parsedQuery = (T) statementParserVisitor.getQuery();
                if ( parsedQuery != null ) {
                    parsedQuery.setHash( queryHash );
                    Dao.getQueryDao().submit( parsedQuery );
                }
                else {
                    Dao.getLogDao().logException( query, "Query parsing not supported!" );
                    throw new QueryParsingNotSupportedException( query );
                }
            }
            return parsedQuery;
        }
        catch ( JSQLParserException e ) {
            Dao.getLogDao().logException( query, e );
            throw new QueryParsingNotSupportedException( query, e );
        }
    }

}
