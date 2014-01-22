package pl.piotrsukiennik.tuner.statement;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.statement.manager.StatementLiveCycleListener;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatementsMonitor {

    private
    @Resource
    List<StatementLiveCycleListener> listeners;

    private ListMultimap<Connection, StatementHolder> statements = Multimaps.synchronizedListMultimap( ArrayListMultimap.<Connection, StatementHolder>create() );

    public StatementHolder add( final Statement stmt, final String sql ) throws SQLException {
        StatementHolder statementHolder = new StatementHolder( sql, stmt );
        statements.put( stmt.getConnection(), statementHolder );
        for ( StatementLiveCycleListener listener : listeners ) {
            listener.onNewStatement( statementHolder );
        }
        return statementHolder;
    }

    public List<StatementHolder> remove( final Connection con ) {
        List<StatementHolder> list = this.statements.removeAll( con );
        for ( StatementLiveCycleListener listener : listeners ) {
            for ( StatementHolder statementHolder : list ) {
                listener.onRemoveStatement( statementHolder );
            }
        }
        return list;
    }

    @ManagedOperation(description = "Current active JDBC statements.")
    public List<String> getCurrentStatements() {
        List<String> retVal = new ArrayList<String>( 100 );
        synchronized ( this.statements ) {
            for ( StatementHolder stmt : statements.values() ) {
                retVal.add( stmt.toString() );
            }
        }

        return retVal;
    }

    @ManagedOperation(description = "Cancel a statement by its ID")
    public void cancel( final int holderIN ) throws SQLException {
        StatementHolder holderToFind = new StatementHolder( holderIN );
        StatementHolder foundHolder = null;
        synchronized ( this.statements ) {
            for ( StatementHolder holder : this.statements.values() ) {
                if ( holder.equals( holderToFind ) ) {
                    foundHolder = holder;
                    break;
                }
            }
        }
        if ( foundHolder != null ) {
            if ( foundHolder.getStmtRef() != null ) {
                foundHolder.getStmtRef().cancel();
            }
        }
        else {
            //if (LOG.isWarnEnabled()) {
            //LOG.warn("No statement '" + holderIN + "' to cancel. Possibly already been removed.");
            //}
        }
    }
}
