package pl.piotrsukiennik.tuner.service;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.query.QueryContext;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public interface QueryElementParserService {
    TableSource getTableSource( QueryContext queryContext, Table tableName );

    TableSource getTableSource( QueryContext queryContext, net.sf.jsqlparser.schema.Column column );

    OrderByFragment getOrderByFragment( QueryContext queryContext, OrderByElement orderByElement );

    ColumnProjection getColumnProjection( QueryContext queryContext, net.sf.jsqlparser.schema.Column tableColumn );

    JoinFragment getJoin( QueryContext queryContext, Join join );

}
