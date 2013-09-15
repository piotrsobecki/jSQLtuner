package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.ValueEntityExpresionParser;
import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.UpdateQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class  UpdateStatementParser<U extends UpdateQuery> extends StatementParser<UpdateQuery>{
    public UpdateStatementParser(QueryContextManager queryContextManager,Update update) {
        super(queryContextManager,update,new UpdateQuery());
    }

    @Override
    public void visit(Update update) {
        TableSource tableSource = parserUtils.getTableSource(update.getTable());
        List<Column> columnList = update.getColumns();
        List<Expression> values = update.getExpressions();
        Set<ColumnValue> valueEntities = new LinkedHashSet<ColumnValue>();
        for (int i=0; i<columnList.size(); i++){
            Column column = columnList.get(i);
            Expression expression = values.get(i);
            pl.piotrsukiennik.tuner.persistance.model.schema.Column column1
                    =  queryContextManager.getColumn(tableSource.getTable().getValue(),column.getColumnName());

            ColumnValue columnValue = new ColumnValue();
            columnValue.setColumn(column1);
            ValueEntityExpresionParser parser = new ValueEntityExpresionParser(queryContextManager,columnValue);
            expression.accept(parser);
            valueEntities.add(columnValue);
        }
        query.setColumnValues(valueEntities);
        super.visit(update);
    }


}
