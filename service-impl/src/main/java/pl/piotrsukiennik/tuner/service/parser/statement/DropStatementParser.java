package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.drop.Drop;
import pl.piotrsukiennik.tuner.model.query.DropQuery;
import pl.piotrsukiennik.tuner.model.query.DropTableQuery;
import pl.piotrsukiennik.tuner.model.query.GenericDropQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DropStatementParser extends StatementParser<DropQuery> {
    public DropStatementParser( ElementParserService elementParserService, QueryContext queryContext, Drop drop ) {
        super( elementParserService, queryContext, drop );
    }

    @Override
    public void visit( Drop drop ) {
        switch ( drop.getType().toLowerCase() ) {
            case "TABLE": {
                DropTableQuery dropTableQuery = new DropTableQuery();
                dropTableQuery.setType( drop.getType() );
                dropTableQuery.setTable( queryContext.getTable( drop.getName() ) );
                this.query = dropTableQuery;
            }
            default: {
                GenericDropQuery genericDropQuery = new GenericDropQuery();
                genericDropQuery.setType( drop.getType() );
                genericDropQuery.setName( drop.getName() );
                this.query = genericDropQuery;
            }
        }
        init( query );
        super.visit( drop );
    }


}
