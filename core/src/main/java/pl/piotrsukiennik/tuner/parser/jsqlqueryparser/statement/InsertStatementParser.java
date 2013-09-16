package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.*;
import pl.piotrsukiennik.tuner.persistance.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.Table;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class InsertStatementParser extends StatementParser<InsertQuery>  {

    public InsertStatementParser(QueryContextManager queryContextManager,Insert insert) {
        super(queryContextManager,insert,null);
    }

    @Override
    public void visit(Insert insert) {
        Table table=  queryContextManager.getTable(insert.getTable().getWholeTableName());
        InsertItemsListParser insertItemsListParser = new InsertItemsListParser(queryContextManager);
        insert.getItemsList().accept(insertItemsListParser);
        query = insertItemsListParser.getInsertQuery();
        init(insert);
        query.setTable(table);


        if (insert.getColumns()!=null){
            Set<pl.piotrsukiennik.tuner.persistance.model.schema.Column> columnSet =
                    new LinkedHashSet<pl.piotrsukiennik.tuner.persistance.model.schema.Column>();
            List<Column> columnList = insert.getColumns();
            for (Column column: columnList){
                columnSet.add(queryContextManager.getColumn(table,column.getColumnName()));
            }
            query.setColumns(columnSet);
        }

        super.visit(insert);
    }

}

