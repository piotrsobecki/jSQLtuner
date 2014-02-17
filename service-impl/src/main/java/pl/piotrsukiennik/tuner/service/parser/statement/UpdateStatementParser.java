package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.model.query.UpdateQuery;
import pl.piotrsukiennik.tuner.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.parser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.service.parser.element.ValueEntityExpresionParser;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class UpdateStatementParser<U extends UpdateQuery> extends StatementParser<UpdateQuery> {
    public UpdateStatementParser( ElementParserService elementParserService, QueryContext queryContext, Update update ) {
        super( elementParserService, queryContext, update, new UpdateQuery() );
    }

    @Override
    public void visit( Update update ) {
        TableSource tableSource = elementParserService.getTableSource( queryContext, update.getTable() );
        List<Column> columnList = update.getColumns();
        List<Expression> values = update.getExpressions();
        Set<ColumnValue> valueEntities = new LinkedHashSet<ColumnValue>();

        for ( int i = 0; i < columnList.size(); i++ ) {
            Column column = columnList.get( i );
            Expression expression = values.get( i );
            pl.piotrsukiennik.tuner.model.schema.Column column1
             = queryContext.getColumn( tableSource.getTable().getValue(), column.getColumnName() );
            ColumnValue columnValue = new ColumnValue();
            columnValue.setColumn( column1 );
            ValueEntityExpresionParser parser = new ValueEntityExpresionParser( elementParserService, queryContext, columnValue );
            expression.accept( parser );
            valueEntities.add( columnValue );
        }

        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
        update.getWhere().accept( expresionParser );
        query.setWhereCondition( expresionParser.getCondition() );
        query.setColumnValues( valueEntities );
        super.visit( update );
    }


}
