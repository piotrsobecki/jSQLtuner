package pl.piotrsukiennik.tuner.parser.impl;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.LoggableMessageEnum;
import pl.piotrsukiennik.tuner.LoggableService;
import pl.piotrsukiennik.tuner.QueryProviderService;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.parser.impl.statement.StatementParserVisitor;
import pl.piotrsukiennik.tuner.service.QueryParsingContextBuilder;
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
@Service("parsingQueryProviderService")
class QueryProviderServiceImpl implements QueryProviderService {

    //private static final Log LOG = LogFactory.getLog( QueryProviderService.class );

    @Autowired
    private QueryParsingContextBuilder<JsqlParserQueryParsingContext> queryParsingContextBuilder;

    @Autowired
    private LoggableService logger;


    @Override
    public <T extends Query> T provide( String database, String schema, String query ) throws QueryParsingNotSupportedException {
        T parsedQuery;
        TimedCallable<T> timedCallable = getParserCallable( database, schema, query );
        try {
            parsedQuery = timedCallable.call();
            logger.log( parsedQuery, LoggableMessageEnum.PARSING, TimeUnit.NANOSECONDS, timedCallable.getDuration( TimeUnit.NANOSECONDS ) );
            return parsedQuery;
        }
        catch ( Exception e ) {
            throw new QueryParsingNotSupportedException( e, query );
        }
    }


    protected <T extends Query> TimedCallable<T> getParserCallable( final String database, final String schema, final String query ) throws QueryParsingNotSupportedException {
        return new TimedCallableImpl<T>( new Callable<T>() {
            @Override
            public T call() throws Exception {
                Statement statement = parse( query );
                return parse( database, schema, query, statement );
            }
        } );
    }

    protected Statement parse( String query ) throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        return pm.parse( new StringReader( query ) );
    }

    protected <T extends Query> T parse( String database, String schema, String query, Statement statement ) throws QueryParsingNotSupportedException {
        JsqlParserQueryParsingContext parsingContext = queryParsingContextBuilder.getQueryContext( database, schema );
        StatementParserVisitor<T> statementParserVisitor = new StatementParserVisitor<T>( parsingContext, statement );
        T mappedQuery = (T) statementParserVisitor.getQuery();
        if ( mappedQuery == null ) {
            throw new QueryParsingNotSupportedException( query );
        }
        return mappedQuery;
    }

}
