package pl.piotrsukiennik.tuner.statement.manager;

import com.google.common.collect.*;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.statement.StatementHolder;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:14
 */

public class StatementRegister implements StatementLiveCycleListener {

    private BiMap<StatementHolder,Statement> statementBiMap = Maps.synchronizedBiMap(HashBiMap.<StatementHolder, Statement>create());

    @Override
    public void onNewStatement(StatementHolder statementHolder) {
        statementBiMap.put(statementHolder, statementHolder.getStmtRef());
    }

    @Override
    public void onRemoveStatement(StatementHolder statementHolder) {
        statementBiMap.remove(statementHolder);
    }


    public StatementHolder get(Statement statement){
        return statementBiMap.inverse().get(statement);
    }

}
