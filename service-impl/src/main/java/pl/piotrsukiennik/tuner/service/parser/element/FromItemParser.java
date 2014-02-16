package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.query.source.SubJoinSource;
import pl.piotrsukiennik.tuner.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.ParsingVisitor;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:24
 */
public class FromItemParser extends ParsingVisitor implements FromItemVisitor {


    private Source source;

    public FromItemParser( QueryElementParserService queryElementParserService, QueryContext queryContext ) {
        super( queryElementParserService, queryContext );
    }


    @Override
    public void visit( Table tableName ) {
        source = queryElementParserService.getTableSource( queryContext, tableName );
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias( subSelect.getAlias() );
        subQuerySource.setValue( subSelect.getSelectBody().toString() );

        SelectQuery selectQuery = new SelectQuery();
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>( queryElementParserService, queryContext, selectQuery );
        subSelect.getSelectBody().accept( selectBodyParser );
        subQuerySource.setSelectQuery( selectQuery );
        source = subQuerySource;
    }

    @Override
    public void visit( SubJoin subjoin ) {
        SubJoinSource subJoinSource = new SubJoinSource();

        JoinFragment joinFragment = queryElementParserService.getJoin( queryContext, subjoin.getJoin() );
        FromItemParser fromItemParser = new FromItemParser( queryElementParserService, queryContext );
        subjoin.getLeft().accept( fromItemParser );

        subJoinSource.setJoinFragment( joinFragment );
        subJoinSource.setAlias( subjoin.getAlias() );
        subJoinSource.setSource( fromItemParser.getSource() );
        subJoinSource.setValue( subjoin.toString() );

        source = subJoinSource;
    }

    public Source getSource() {
        return source;
    }
}
