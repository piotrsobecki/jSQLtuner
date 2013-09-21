package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.ParsingVisitor;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.Visitor;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.persistance.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;
import pl.piotrsukiennik.tuner.persistance.model.query.source.SubJoinSource;
import pl.piotrsukiennik.tuner.persistance.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.QueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:24
 */
public class FromItemParser extends ParsingVisitor implements FromItemVisitor {


    private Source source;

    public FromItemParser(QueryContextManager queryContextManager) {
        super(queryContextManager);
    }


    @Override
    public void visit(Table tableName) {
        source = parserUtils.getTableSource(tableName);
    }

    @Override
    public void visit(SubSelect subSelect) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias(subSelect.getAlias());
        subQuerySource.setValue(subSelect.getSelectBody().toString());

        SelectQuery selectQuery = new SelectQuery();
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>(queryContextManager,selectQuery);
        subSelect.getSelectBody().accept(selectBodyParser);
        subQuerySource.setSelectQuery(selectQuery);
        source=subQuerySource;
    }

    @Override
    public void visit(SubJoin subjoin) {
        SubJoinSource subJoinSource = new SubJoinSource();

        JoinFragment joinFragment = parserUtils.getJoin(subjoin.getJoin());
        FromItemParser fromItemParser = new FromItemParser(queryContextManager);
        subjoin.getLeft().accept(fromItemParser);

        subJoinSource.setJoinFragment(joinFragment);
        subJoinSource.setAlias(subjoin.getAlias());
        subJoinSource.setSource(fromItemParser.getSource());
        subJoinSource.setValue(subjoin.toString());

        source=subJoinSource;
    }

    public Source getSource() {
        return source;
    }
}
