package pl.piotrsukiennik.tuner.cache;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.cache.model.HashesHolderManager;
import pl.piotrsukiennik.tuner.cache.model.build.EnterableHolderFactory;
import pl.piotrsukiennik.tuner.persistance.model.query.*;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.SourceProjection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;
import pl.piotrsukiennik.tuner.persistance.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.Column;
import pl.piotrsukiennik.tuner.persistance.model.schema.Database;
import pl.piotrsukiennik.tuner.persistance.model.schema.Schema;
import pl.piotrsukiennik.tuner.persistance.model.schema.Table;

import java.util.*;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:04
 */
@Component
public class CacheManager {

    public static final String STAR_PROJECTION_COLUMN_NAME = "*";

    private HashesHolderManager<String,SelectQuery> hashesHolderManager;

    public CacheManager() {
        EnterableHolderFactory holderFactory  = new EnterableHolderFactory(LinkedHashSet.class);
        hashesHolderManager = new HashesHolderManager<String,SelectQuery>(holderFactory);
    }

    public void putCachedQuery(SelectQuery query){
        putCachedQuery(query, query);
    }
    protected void putCachedQuery(SelectQuery objectToParseForPath, SelectQuery objectToStore){
        if(objectToParseForPath.getProjections()!=null){
            for (Projection projection: objectToParseForPath.getProjections()){
                if (projection instanceof ColumnProjection){
                    hashesHolderManager.put(getPath(((ColumnProjection) projection).getColumn()), objectToStore);
                } else if (projection instanceof SourceProjection){
                    Source source =  ((SourceProjection) projection).getSource();
                    if (source instanceof SubQuerySource){
                        putCachedQuery(((SubQuerySource) source).getSelectQuery(), objectToStore);
                    } else if (source instanceof TableSource){
                        hashesHolderManager.put(getStarPath(((TableSource)source).getTable()), objectToStore);
                    }
                }
            }
        }
    }
    public Collection<SelectQuery> getQueriesToInvalidate(Query query){
        if (query instanceof UpdateQuery){
            UpdateQuery writeQuery = (UpdateQuery)query;
            Collection<SelectQuery> queriesToInvalidate = new LinkedHashSet<SelectQuery>();
            for (Values values: writeQuery.getValues()){
                Set<Table> tablesToStarInvalidate = new LinkedHashSet<Table>();
                for(ColumnValue columnValue: values.getColumnValues()){
                    tablesToStarInvalidate.add(columnValue.getColumn().getTable());
                    String[] path1 = getPath(columnValue.getColumn());
                    queriesToInvalidate.addAll(hashesHolderManager.getRemove(path1));
                }
                for (Table table:tablesToStarInvalidate){
                    String[] path2 = getStarPath(table);
                    queriesToInvalidate.addAll(hashesHolderManager.getRemove(path2));
                }
            }
            return queriesToInvalidate;
        }
        return Collections.EMPTY_LIST;
    }
    protected String[] getPath(Column column){
        Table table =  column.getTable();
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return  new String[]{database.getValue(),schema.getValue(),table.getValue(),column.getValue()};
    }
    protected String[] getStarPath(Table table){
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return  new String[]{database.getValue(),schema.getValue(),table.getValue(),STAR_PROJECTION_COLUMN_NAME};
    }
}
