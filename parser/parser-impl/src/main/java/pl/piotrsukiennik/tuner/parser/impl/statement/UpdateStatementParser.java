package pl.piotrsukiennik.tuner.parser.impl.statement;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.impl.UpdateQuery;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.parser.impl.element.ExpresionParser;
import pl.piotrsukiennik.tuner.parser.impl.element.ValueEntityExpresionParser;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class UpdateStatementParser<U extends UpdateQuery> extends StatementParser<UpdateQuery> {
    public UpdateStatementParser( QueryParsingContext parsingContext, Update update ) {
        super( parsingContext, update, new UpdateQuery() );
    }

    @Override
    public void visit( Update update ) {
        TableSource tableSource = parsingContext.getTableSource( update.getTable() );
        List<Column> columnList = update.getColumns();
        List<Expression> values = update.getExpressions();
        Set<ColumnValue> valueEntities = new LinkedHashSet<ColumnValue>();

        for ( int i = 0; i < columnList.size(); i++ ) {
            Column column = columnList.get( i );
            Expression expression = values.get( i );
            pl.piotrsukiennik.tuner.model.schema.Column column1 = parsingContext.getColumn( tableSource.getTable(), column );
            ColumnValue columnValue = new ColumnValue();
            columnValue.setColumn( column1 );
            ValueEntityExpresionParser parser = new ValueEntityExpresionParser( parsingContext, columnValue );
            expression.accept( parser );
            valueEntities.add( columnValue );
        }

        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        update.getWhere().accept( expresionParser );
        query.setWhereExpression( (OperatorExpression) expresionParser.getExpression() );
        query.setColumnValues( valueEntities );
        super.visit( update );
    }


}
