package pl.piotrsukiennik.tuner.parser.jsqlqueryparser;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.FromItemParser;
import pl.piotrsukiennik.tuner.persistance.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 18:07
 */
public class ParserUtils {

    private QueryContextManager queryContextManager;

    public ParserUtils(QueryContextManager queryContextManager){
        this.queryContextManager=queryContextManager;
    }

    public TableSource getTableSource(Table tableName) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias(tableName.getAlias());
        tableSource.setValue(tableName.getWholeTableName() + (tableName.getAlias() == null ? "" : (" " + tableName.getAlias())));
        tableSource.setTable(queryContextManager.getTable(tableName.getWholeTableName()));
        queryContextManager.putTableSource(tableSource);
        return tableSource;
    }

    public TableSource getTableSource(net.sf.jsqlparser.schema.Column column) {
        return queryContextManager.getTableSource(column.getTable().getWholeTableName());
    }

    public OrderByFragment getOrderByFragment(OrderByElement orderByElement){
        OrderByFragment orderByFragment = new OrderByFragment();
        orderByFragment.setOrderDirection(orderByElement.isAsc() ? OrderByFragment.Order.ASC : OrderByFragment.Order.DESC);
        ExpresionParser orderByExpresionParser = new ExpresionParser(queryContextManager);
        orderByElement.getExpression().accept(orderByExpresionParser);
        orderByFragment.setOrderByExpression(orderByExpresionParser.getCondition());
        return orderByFragment;
    }

    public ColumnProjection getColumnProjection(net.sf.jsqlparser.schema.Column tableColumn){
        TableSource tableSource =null;

        if (tableColumn.getTable().getName()==null){
            tableSource = queryContextManager.getLastAttachedTableSource();
        } else {
            tableSource =  getTableSource(tableColumn);
        }
        if (tableSource==null){
            throw new RuntimeException("Null tableSource exception");
        }

        pl.piotrsukiennik.tuner.persistance.model.schema.Column col = queryContextManager.getColumn(tableSource.getTable(), tableColumn.getColumnName());
        ColumnProjection columnProjection = new ColumnProjection();
        columnProjection.setColumn(col);
        columnProjection.setSource(tableSource);
        return columnProjection;
    }



    public JoinFragment getJoin(Join join){

        JoinFragment joinFragment = new JoinFragment();
        joinFragment.setValue(join.toString());

        FromItemParser joinFromItemParser = new FromItemParser(queryContextManager);
        join.getRightItem().accept(joinFromItemParser);
        joinFragment.setSource(joinFromItemParser.getSource());

        ExpresionParser parser = new ExpresionParser(queryContextManager);
        join.getOnExpression().accept(parser);
        joinFragment.setOn(parser.getCondition());
        return joinFragment;
    }

}
