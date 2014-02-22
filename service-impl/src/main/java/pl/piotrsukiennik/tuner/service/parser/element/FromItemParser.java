package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.SubJoinSource;
import pl.piotrsukiennik.tuner.model.source.SubQuerySource;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.service.parser.statement.ParsingVisitor;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:24
 */
public class FromItemParser extends ParsingVisitor implements FromItemVisitor {


    private Source source;

    public FromItemParser( QueryParsingContext parsingContext ) {
        super( parsingContext );
    }


    @Override
    public void visit( Table tableName ) {
        source = parsingContext.getTableSource( tableName );
    }

    @Override
    public void visit( LateralSubSelect lateralSubSelect ) {
        //TODO
    }

    @Override
    public void visit( ValuesList valuesList ) {
        //TODO
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias( parsingContext.getTableAlias( subSelect.getAlias() ) );
        subQuerySource.setValue( subSelect.getSelectBody().toString() );

        SelectQuery selectQuery = new SelectQuery();
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>( parsingContext, selectQuery );
        subSelect.getSelectBody().accept( selectBodyParser );
        subQuerySource.setSelectQuery( selectQuery );
        source = subQuerySource;
    }

    @Override
    public void visit( SubJoin subjoin ) {
        SubJoinSource subJoinSource = new SubJoinSource();

        JoinFragment joinFragment = parsingContext.getJoin( subjoin.getJoin() );
        FromItemParser fromItemParser = new FromItemParser( parsingContext );
        subjoin.getLeft().accept( fromItemParser );

        subJoinSource.setJoinFragment( joinFragment );
        subJoinSource.setAlias( parsingContext.getTableAlias( subjoin.getAlias() ) );
        subJoinSource.setSource( fromItemParser.getSource() );
        subJoinSource.setValue( subjoin.toString() );

        source = subJoinSource;
    }

    public Source getSource() {
        return source;
    }
}
