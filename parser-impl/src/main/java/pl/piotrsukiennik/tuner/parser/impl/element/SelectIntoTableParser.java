package pl.piotrsukiennik.tuner.parser.impl.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.parser.NewQueryUtils;
import pl.piotrsukiennik.tuner.parser.impl.statement.Visitor;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class SelectIntoTableParser extends Visitor implements IntoTableVisitor {

    private SelectQuery sourceQuery;


    public SelectIntoTableParser( JsqlParserQueryParsingContext parsingContext, SelectQuery sourceQuery ) {
        super( parsingContext );
        this.sourceQuery = sourceQuery;
    }

    @Override
    public void visit( Table tableName ) {
        TableSource tableSource = parsingContext.getTableSource( tableName );
        NewQueryUtils.addSource( sourceQuery, tableSource );
    }

}
