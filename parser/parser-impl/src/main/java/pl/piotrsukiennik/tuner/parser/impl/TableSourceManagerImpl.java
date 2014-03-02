package pl.piotrsukiennik.tuner.parser.impl;

import org.apache.commons.lang.StringUtils;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.parser.TableSourceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public class TableSourceManagerImpl implements TableSourceManager {

    private Map<String, TableSource> tableNames = new HashMap<String, TableSource>();

    private TableSource lastAttachedTableSource;


    @Override
    public TableSource mergeTableSource( TableSource tableSource ) {
        String identifier = null;
        if ( StringUtils.isEmpty( tableSource.getAlias() ) ) {
            identifier = tableSource.getValue();
        }
        else {
            identifier = tableSource.getAlias();
        }
        TableSource tableSourceMerged = tableNames.get( identifier );
        if ( tableSourceMerged == null ) {
            tableNames.put( identifier, tableSource );
            tableSourceMerged = tableSource;
        }
        lastAttachedTableSource = tableSourceMerged;
        return tableSourceMerged;
    }


    @Override
    public TableSource getLastAttachedTableSource() {
        return lastAttachedTableSource;
    }

}
