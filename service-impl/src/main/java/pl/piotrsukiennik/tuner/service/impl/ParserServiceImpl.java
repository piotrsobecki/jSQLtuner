package pl.piotrsukiennik.tuner.service.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.LoggableMessageEnum;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.LoggableServiceHolder;
import pl.piotrsukiennik.tuner.service.ParserService;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.StatementParserVisitor;
import pl.piotrsukiennik.tuner.util.TimedCallable;
import pl.piotrsukiennik.tuner.util.TimedCallableImpl;

import java.io.StringReader;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 19:05
 */
@Service
@Transactional(value = "jsqlTunerTransactionManager")
class ParserServiceImpl implements ParserService {

    private static final Log LOG = LogFactory.getLog( ParserService.class );

    @Autowired
    private ElementParserService elementParserService;

    @Autowired
    private LoggableServiceHolder logService;

    @Override
    public <T extends Query> T parse( String database, String schema, String query ) throws QueryParsingNotSupportedException {
        T parsedQuery;
        QueryContext queryContext = new QueryContextImpl( database, schema );
        TimedCallable<T> timedCallable = getQuery( queryContext, query );
        try {
            parsedQuery = timedCallable.call();
            logService.log( parsedQuery, LoggableMessageEnum.PARSING, TimeUnit.NANOSECONDS, timedCallable.getTime( TimeUnit.NANOSECONDS ) );
            return parsedQuery;
        }
        catch ( Exception e ) {
            throw new QueryParsingNotSupportedException( e, query );
        }
    }


    protected <T extends Query> TimedCallable<T> getQuery( final QueryContext queryContext, final String query ) throws QueryParsingNotSupportedException {
        return new TimedCallableImpl<T>( new Callable<T>() {
            @Override
            public T call() throws Exception {
                return parse( queryContext, query );
            }
        } );
    }

    protected <T extends Query> T parse( final QueryContext queryContext, String query ) throws QueryParsingNotSupportedException {
        try {
            Statement statement = parse( query );
            return parse( queryContext, query, statement );
        }
        catch ( JSQLParserException e ) {
            throw new QueryParsingNotSupportedException( e, query );
        }
    }

    protected Statement parse( String query ) throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        return pm.parse( new StringReader( query ) );
    }

    protected <T extends Query> T parse( final QueryContext queryContext, String query, Statement statement ) throws QueryParsingNotSupportedException {
        StatementParserVisitor<T> statementParserVisitor = new StatementParserVisitor<T>( elementParserService, queryContext, statement );
        T mappedQuery = (T) statementParserVisitor.getQuery();
        if ( mappedQuery == null ) {
            throw new QueryParsingNotSupportedException( query );
        }
        return mappedQuery;
    }

}
