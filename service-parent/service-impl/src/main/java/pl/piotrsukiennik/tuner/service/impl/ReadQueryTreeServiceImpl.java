package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.SourceProjection;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.SubQuerySource;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.service.ReadQueryTreeService;
import pl.piotrsukiennik.tuner.tree.Tree;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
@Service
public class ReadQueryTreeServiceImpl implements ReadQueryTreeService<ReadQuery> {

    public static final String STAR_PROJECTION_COLUMN_NAME = "*";

    @Resource(name = "readQueryTree")
    private Tree<String, ReadQuery> tree;

    @Override
    public Collection<ReadQuery> getQueriesInvalidatedBy( Table table ) {
        return tree.get( getPath( table ) );
    }

    @Override
    public Collection<ReadQuery> getQueriesInvalidatedBy( Column column ) {
        return tree.get( getPath( column ) );
    }

    @Override
    public Collection<ReadQuery> getQueriesInvalidatedByStar( Table table ) {
        return tree.get( getStarPath( table ) );
    }

    @Override
    public void putQuery( ReadQuery query ) {
        putQuery( query, query );

    }

    protected void putQuery( ReadQuery objectToParseForPath, ReadQuery objectToStore ) {

        if ( objectToParseForPath instanceof SelectQuery ) {
            SelectQuery selectQuery = (SelectQuery) objectToParseForPath;
            if ( selectQuery.getProjections() != null ) {
                for ( Expression projection : selectQuery.getProjections() ) {
                    if ( projection instanceof ColumnProjection ) {
                        tree.put( getPath( ( (ColumnProjection) projection ).getColumn() ), objectToStore );
                    }
                    else if ( projection instanceof SourceProjection ) {
                        Source source = ( (SourceProjection) projection ).getSource();
                        if ( source instanceof SubQuerySource ) {
                            putQuery( ( (SubQuerySource) source ).getSelectQuery(), objectToStore );
                        }
                        else if ( source instanceof TableSource ) {
                            tree.put( getStarPath( ( (TableSource) source ).getTable() ), objectToStore );
                        }
                    }
                }
            }
        }

    }


    protected String[] getPath( Table table ) {
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return new String[] { database.getValue(), schema.getValue(), table.getValue() };
    }

    protected String[] getPath( Column column ) {
        Table table = column.getTable();
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return new String[] { database.getValue(), schema.getValue(), table.getValue(), column.getValue() };
    }

    protected String[] getStarPath( Table table ) {
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return new String[] { database.getValue(), schema.getValue(), table.getValue(), STAR_PROJECTION_COLUMN_NAME };
    }
}
