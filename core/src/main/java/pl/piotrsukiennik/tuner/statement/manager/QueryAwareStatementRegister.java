package pl.piotrsukiennik.tuner.statement.manager;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.statement.StatementHolder;
import pl.piotrsukiennik.tuner.utils.OrderedComparator;

import javax.annotation.Resource;
import java.sql.Statement;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:35
 */
@Component
public class QueryAwareStatementRegister extends StatementRegister implements QueryInitializationListener {

    private Collection<QueryCompletionListener> invalidationListeners;

    @Override
    public void onRemoveStatement( StatementHolder statementHolder ) {
        super.onRemoveStatement( statementHolder );
        if ( statementHolder.getQuery() != null ) {
            for ( QueryCompletionListener queryInvalidationListener : invalidationListeners ) {
                queryInvalidationListener.queryCompleted( statementHolder.getQuery() );
            }
        }
    }

    @Override
    public void onNewQuery( Query query, Statement statement ) {
        StatementHolder statementHolder = get( statement );
        if ( statementHolder != null ) {
            statementHolder.setQuery( query );
        }
    }

    @Override
    public Integer getOrder() {
        return Integer.MIN_VALUE;
    }


    public Collection<QueryCompletionListener> getInvalidationListeners() {
        return invalidationListeners;
    }

    @Resource
    public void setInvalidationListeners( Collection<QueryCompletionListener> invalidationListeners ) {
        TreeSet<QueryCompletionListener> treeSet = new TreeSet<QueryCompletionListener>( new OrderedComparator() );
        treeSet.addAll( invalidationListeners );
        this.invalidationListeners = invalidationListeners;
    }
}
