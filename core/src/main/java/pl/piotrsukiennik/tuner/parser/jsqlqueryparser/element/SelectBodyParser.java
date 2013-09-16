package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.Union;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.ParsingVisitor;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

import java.util.ArrayList;
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
        FromItemParser fromItemParser = new FromItemParser(queryContextManager,query);
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager);
        if (plainSelect.getFromItem()!=null){
            plainSelect.getFromItem().accept(fromItemParser);
        }

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectItemParser selectItemParser =  new SelectItemParser(queryContextManager,query);
        for (SelectItem selectItem: selectItems){
            selectItem.accept(selectItemParser);
        }

        if (plainSelect.getHaving()!=null){
            plainSelect.getHaving().accept(expresionParser);
        }

        if (plainSelect.getWhere()!=null){
            plainSelect.getWhere().accept(expresionParser);
            query.setWhereCondition(expresionParser.getCondition());
        }

        if (query instanceof SelectQuery){
            SelectIntoTableParser selectIntoTableParser = new SelectIntoTableParser(queryContextManager,(SelectQuery)query);
            if(plainSelect.getInto()!=null){
                plainSelect.getInto().accept(fromItemParser);
            }
        }

        init(plainSelect);

    }

    @Override
    public void visit(Union union) {
        init(union);
    }
}
