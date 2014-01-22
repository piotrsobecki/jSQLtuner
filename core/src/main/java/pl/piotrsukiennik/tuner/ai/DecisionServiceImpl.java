package pl.piotrsukiennik.tuner.ai;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.IDecisionService;
import pl.piotrsukiennik.tuner.model.query.IQuery;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryParserService;
import pl.piotrsukiennik.tuner.statement.PreparedStatementProxyCreator;
import pl.piotrsukiennik.tuner.statement.manager.QueryInitializationListener;
import pl.piotrsukiennik.tuner.statement.manager.StatementRegister;
import pl.piotrsukiennik.tuner.utils.OrderedComparator;
import pl.piotrsukiennik.tuner.utils.Statements;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:18
 */
@Service
public class DecisionServiceImpl implements IDecisionService {

    private
    @Resource
    StatementRegister register;

    private
    @Resource
    QueryParserService parser;

    private
    @Resource
    PreparedStatementProxyCreator preparedStatementProxyCreator;


    private Collection<QueryInitializationListener> listeners;

    @Inject
    @Qualifier("jsqltunerIgnoreSchema")
    private List<String> ignoreSchema;

    @Override
    public boolean checkToProxy( String schema ) {
        return !ignoreSchema.contains( schema );
    }

    @Override
    public boolean checkProceed( IQuery query ) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PreparedStatement proceed( PreparedStatement source, Query query ) throws Throwable {

        return preparedStatementProxyCreator.create( query, source );
    }


    public PreparedStatement proceed( PreparedStatement source, Connection connection, String queryString ) throws Throwable {
        String schema = Statements.getSchema( connection );
        if ( checkToProxy( schema ) ) {
            Query query = parser.parse( schema, schema, queryString );
            if ( query != null ) {
                for ( QueryInitializationListener queryInitializationListener : listeners ) {
                    queryInitializationListener.onNewQuery( query, source );
                }
            }
            return proceed( source, query );
        }
        return source;
    }


    public Collection<QueryInitializationListener> getListeners() {
        return listeners;
    }

    @Resource
    public void setListeners( Collection<QueryInitializationListener> listeners ) {
        TreeSet<QueryInitializationListener> treeSet = new TreeSet<QueryInitializationListener>( new OrderedComparator() );
        treeSet.addAll( listeners );
        this.listeners = listeners;
    }
}
