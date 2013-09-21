package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.ParsingVisitor;
import pl.piotrsukiennik.tuner.persistance.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.QueryUtils;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:54
 */
public class SelectBodyParser<T extends SelectQuery> extends ParsingVisitor<T> implements net.sf.jsqlparser.statement.select.SelectVisitor {
    public SelectBodyParser(QueryContextManager queryContextManager, T query) {
        super(queryContextManager,query);
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        FromItemParser fromItemParser = new FromItemParser(queryContextManager);
        plainSelect.getFromItem().accept(fromItemParser);
        QueryUtils.addSource(query, fromItemParser.getSource());
        if (plainSelect.getFromItem()!=null){
            plainSelect.getFromItem().accept(fromItemParser);
        }

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectItemParser selectItemParser =  new SelectItemParser(queryContextManager,query);
        for (SelectItem selectItem: selectItems){
            selectItem.accept(selectItemParser);
        }

        ExpresionParser expresionParser = new ExpresionParser(queryContextManager);

        if (plainSelect.getHaving()!=null){
            plainSelect.getHaving().accept(expresionParser);
            query.setHavingCondition(expresionParser.getCondition());
        }

        if (plainSelect.getWhere()!=null){
            plainSelect.getWhere().accept(expresionParser);
            query.setWhereCondition(expresionParser.getCondition());
        }

        if (plainSelect.getJoins()!=null){
            List<Join> joins = plainSelect.getJoins();
            for (Join join: joins){
                JoinFragment joinFragment = parserUtils.getJoin(join);
                QueryUtils.addJoin(query,joinFragment);
            }
        }

        if (plainSelect.getOrderByElements()!=null){
            List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
            for (int i=0; i<orderByElements.size();i++){
                OrderByElement orderByElement = orderByElements.get(i);
                OrderByFragment orderByFragment =parserUtils.getOrderByFragment(orderByElement);
                QueryUtils.addOrderByFragment(query,orderByFragment);
            }

        }

        if (plainSelect.getGroupByColumnReferences()!=null){
            List<Column> groupByColumns = plainSelect.getGroupByColumnReferences();
            for (Column column: groupByColumns){
                ColumnProjection columnProjection = parserUtils.getColumnProjection(column);
                QueryUtils.addGroupByFragment(query,columnProjection);
            }

        }

        if(plainSelect.getInto()!=null){
           query.setIntoTable(queryContextManager.getTable(plainSelect.getInto().getWholeTableName()));
        }


        if (plainSelect.getLimit()!=null){
            Limit limit = plainSelect.getLimit();
            query.setLimitFrom(limit.getOffset());
            query.setLimitTo(limit.getOffset()+limit.getRowCount());
        }

        init(plainSelect);

    }

    @Override
    public void visit(Union union) {
        init(union);
    }
}
