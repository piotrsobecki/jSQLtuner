package pl.piotrsukiennik.tuner.service.transactional;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.QueryParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.StatementParserVisitor;
import pl.piotrsukiennik.tuner.service.query.QueryContext;
import pl.piotrsukiennik.tuner.utils.HashGenerators;

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

    @Resource
    private QueryElementParserService queryElementParserService;

    protected String getQueryHash( String databaseStr, String schemaStr, Statement statement ) {
        return HashGenerators.MD5.getHash( databaseStr + "." + schemaStr + "." + statement.toString() );
    }


    @Override
    public Query parse( String databaseStr, String schemaStr, String query ) {
        try {
            CCJSqlParserManager pm = new CCJSqlParserManager();
            QueryContext queryContext = new QueryContext();
            queryContext.getDatabase( databaseStr );
            queryContext.getSchema( schemaStr );
            Statement statement = pm.parse( new StringReader( query ) );
            String queryHash = getQueryHash( databaseStr, schemaStr, statement );
            Query parsedQuery = DaoHolder.getQueryDao().getQueryByHash( queryHash );
            if ( parsedQuery == null ) {
                StatementParserVisitor statementParserVisitor = new StatementParserVisitor( queryElementParserService, queryContext, statement );
                parsedQuery = statementParserVisitor.getQuery();
                parsedQuery.setHash( queryHash );
            }
            return parsedQuery;
        }
        catch ( JSQLParserException e ) {
            DaoHolder.getLogDao().logException( query, e );
        }
        return null;
    }

}
