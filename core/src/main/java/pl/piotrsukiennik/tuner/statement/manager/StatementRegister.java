package pl.piotrsukiennik.tuner.statement.manager;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import pl.piotrsukiennik.tuner.statement.StatementHolder;

import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:14
 */

public class StatementRegister implements StatementLiveCycleListener {

    private BiMap<StatementHolder, Statement> statementBiMap = Maps.synchronizedBiMap( HashBiMap.<StatementHolder, Statement>create() );

    @Override
    public void onNewStatement( StatementHolder statementHolder ) {
        statementBiMap.put( statementHolder, statementHolder.getStmtRef() );
    }

    @Override
    public void onRemoveStatement( StatementHolder statementHolder ) {
        statementBiMap.remove( statementHolder );
    }


    public StatementHolder get( Statement statement ) {
        return statementBiMap.inverse().get( statement );
    }

}
