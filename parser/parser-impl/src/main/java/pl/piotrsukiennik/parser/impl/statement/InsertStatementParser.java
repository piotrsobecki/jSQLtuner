package pl.piotrsukiennik.parser.impl.statement;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import pl.piotrsukiennik.parser.QueryParsingContext;
import pl.piotrsukiennik.parser.impl.element.InsertItemsListParser;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.schema.Table;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class InsertStatementParser extends StatementParser<InsertQuery> {

    public InsertStatementParser( QueryParsingContext parsingContext, Insert insert ) {
        super( parsingContext, insert );
    }

    @Override
    public void visit( Insert insert ) {
        Table table = parsingContext.getTable( insert.getTable() );
        InsertItemsListParser insertItemsListParser = new InsertItemsListParser( parsingContext );
        insert.getItemsList().accept( insertItemsListParser );
        query = insertItemsListParser.getInsertQuery();
        init( insert );
        query.setTable( table );
        if ( insert.getColumns() != null ) {
            Set<pl.piotrsukiennik.tuner.model.schema.Column> columnSet =
             new LinkedHashSet<pl.piotrsukiennik.tuner.model.schema.Column>();
            List<Column> columnList = insert.getColumns();
            for ( Column column : columnList ) {
                columnSet.add( parsingContext.getColumn( table, column ) );
            }
            query.setColumns( columnSet );
        }
        super.visit( insert );
    }

}

