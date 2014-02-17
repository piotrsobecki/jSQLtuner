package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.parser.element.InsertItemsListParser;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class InsertStatementParser extends StatementParser<InsertQuery> {

    public InsertStatementParser( ElementParserService elementParserService, QueryContext queryContext, Insert insert ) {
        super( elementParserService, queryContext, insert, null );
    }

    @Override
    public void visit( Insert insert ) {
        Table table = NewQueryUtils.map( queryContext, insert.getTable() );
        InsertItemsListParser insertItemsListParser = new InsertItemsListParser( elementParserService, queryContext );
        insert.getItemsList().accept( insertItemsListParser );
        query = insertItemsListParser.getInsertQuery();
        init( insert );
        query.setTable( table );

        if ( insert.getColumns() != null ) {
            Set<pl.piotrsukiennik.tuner.model.schema.Column> columnSet =
             new LinkedHashSet<pl.piotrsukiennik.tuner.model.schema.Column>();
            List<Column> columnList = insert.getColumns();
            for ( Column column : columnList ) {
                columnSet.add( queryContext.getColumn( table, column.getColumnName() ) );
            }
            query.setColumns( columnSet );
        }

        super.visit( insert );
    }

}

