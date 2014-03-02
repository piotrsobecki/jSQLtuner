package pl.piotrsukiennik.tuner.parser.impl.statement;

import net.sf.jsqlparser.statement.drop.Drop;
import pl.piotrsukiennik.tuner.model.query.DropQuery;
import pl.piotrsukiennik.tuner.model.query.impl.DropTableQuery;
import pl.piotrsukiennik.tuner.model.query.impl.GenericDropQuery;
import pl.piotrsukiennik.tuner.parser.QueryParsingContext;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DropStatementParser extends StatementParser<DropQuery> {
    public DropStatementParser( QueryParsingContext parsingContext, Drop drop ) {
        super( parsingContext, drop );
    }

    @Override
    public void visit( Drop drop ) {
        switch ( drop.getType().toLowerCase() ) {
            case "TABLE": {
                DropTableQuery dropTableQuery = new DropTableQuery();
                dropTableQuery.setType( drop.getType() );
                dropTableQuery.setTable( parsingContext.getTable( drop.getName() ) );
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
