package pl.piotrsukiennik.tuner.service.parser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.query.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryContext;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public interface ElementParserService {
    TableSource getTableSource( QueryContext queryContext, Table tableName );

    TableSource getTableSource( QueryContext queryContext, net.sf.jsqlparser.schema.Column column );

    OrderByFragment getOrderByFragment( QueryContext queryContext, OrderByElement orderByElement );

    ColumnProjection getColumnProjection( QueryContext queryContext, net.sf.jsqlparser.schema.Column tableColumn );

    StarProjection getStarProjection( QueryContext queryContext, AllColumns allColumns, Source source );

    StarProjection getStarProjection( QueryContext queryContext, AllTableColumns allColumns );

    JoinFragment getJoin( QueryContext queryContext, Join join );

}
