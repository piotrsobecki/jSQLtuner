package pl.piotrsukiennik.tuner.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jmx.export.annotation.ManagedOperation;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import org.springframework.stereotype.Component;

@Component
public class StatementsMonitor {

private ListMultimap<Connection, StatementHolder> statements = Multimaps.synchronizedListMultimap(ArrayListMultimap
.<Connection, StatementHolder> create());

public void add(final Statement stmt, final String sql) throws SQLException {
    statements.put(stmt.getConnection(), new StatementHolder(sql, stmt));
}

public void remove(final Connection con) {
    this.statements.removeAll(con);
    }

@ManagedOperation(description = "Current active JDBC statements.")
public List<String> getCurrentStatements() {
List<String> retVal = new ArrayList<String>(100);
synchronized (this.statements) {
for (StatementHolder stmt : statements.values()) {
retVal.add(stmt.toString());
}
}

return retVal;
}

@ManagedOperation(description = "Cancel a statement by its ID")
public void cancel(final int holderIN) throws SQLException {
    StatementHolder holderToFind = new StatementHolder(holderIN);
    StatementHolder foundHolder = null;
    synchronized (this.statements) {
    for (StatementHolder holder : this.statements.values()) {
         if (holder.equals(holderToFind)) {
         foundHolder = holder;
         break;
        }
    }
}
if (foundHolder != null) {
    if (foundHolder.getStmtRef() != null) {
        foundHolder.getStmtRef().cancel();
    }
} else {
    //if (LOG.isWarnEnabled()) {
    //LOG.warn("No statement '" + holderIN + "' to cancel. Possibly already been removed.");
//}
}
}
}