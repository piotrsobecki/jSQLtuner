package pl.piotrsukiennik.tuner.service.transactional;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.query.condition.PairCondition;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.service.parser.element.FromItemParser;
import pl.piotrsukiennik.tuner.service.query.QueryContext;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 18:07
 */
@Service
@Transactional( value = "jsqlTunerTransactionManager" )
public class QueryElementParserServiceImpl implements QueryElementParserService {


    public PairCondition getCondition( QueryContext queryContext, Condition leftCondition, Condition rightCondition ) {
        PairCondition pairCondition = new PairCondition();
        pairCondition.setLeftCondition( leftCondition );
        pairCondition.setRightCondition( rightCondition );
        DaoHolder.getCommonDao().create( pairCondition );
        return pairCondition;
    }


    @Override
    public TableSource getTableSource( QueryContext queryContext, Table tableName ) {
        TableSource tableSource = new TableSource();
        String tableValue = tableName.getWholeTableName();
        if ( tableName.getAlias() == null ) {
            tableValue += " " + tableName.getAlias();
        }
        tableSource.setAlias( tableName.getAlias() );
        tableSource.setValue( tableValue );
        tableSource.setTable( queryContext.getTable( tableName.getWholeTableName() ) );
        tableSource = queryContext.mergeTableSource( tableSource );
        if ( tableSource.getId() == 0 ) {
            DaoHolder.getCommonDao().create( tableSource );
        }
        return tableSource;
    }

    @Override
    public TableSource getTableSource( QueryContext queryContext, net.sf.jsqlparser.schema.Column column ) {
        return queryContext.getTableSource( column.getTable().getWholeTableName() );
    }

    @Override
    public OrderByFragment getOrderByFragment( QueryContext queryContext, OrderByElement orderByElement ) {
        OrderByFragment orderByFragment = new OrderByFragment();
        orderByFragment.setOrderDirection( orderByElement.isAsc() ? OrderByFragment.Order.ASC : OrderByFragment.Order.DESC );
        ExpresionParser orderByExpresionParser = new ExpresionParser( this, queryContext );
        orderByElement.getExpression().accept( orderByExpresionParser );
        orderByFragment.setOrderByExpression( orderByExpresionParser.getCondition() );
        DaoHolder.getCommonDao().create( orderByFragment );
        return orderByFragment;
    }

    @Override
    public ColumnProjection getColumnProjection( QueryContext queryContext, net.sf.jsqlparser.schema.Column tableColumn ) {
        TableSource tableSource = null;
        if ( tableColumn.getTable().getName() == null ) {
            tableSource = queryContext.getLastAttachedTableSource();
        }
        else {
            tableSource = getTableSource( queryContext, tableColumn );
        }
        if ( tableSource == null ) {
            throw new RuntimeException( "TableSource cannot be obtained for column." );
        }
        pl.piotrsukiennik.tuner.model.schema.Column col = queryContext.getColumn( tableSource.getTable(), tableColumn.getColumnName() );
        ColumnProjection columnProjection = new ColumnProjection();
        columnProjection.setColumn( col );
        columnProjection.setSource( tableSource );
        DaoHolder.getCommonDao().create( columnProjection );
        return columnProjection;
    }


    @Override
    public JoinFragment getJoin( QueryContext queryContext, Join join ) {

        JoinFragment joinFragment = new JoinFragment();
        joinFragment.setValue( join.toString() );

        FromItemParser joinFromItemParser = new FromItemParser( this, queryContext );
        join.getRightItem().accept( joinFromItemParser );
        joinFragment.setSource( joinFromItemParser.getSource() );

        ExpresionParser parser = new ExpresionParser( this, queryContext );
        join.getOnExpression().accept( parser );
        joinFragment.setOn( parser.getCondition() );
        DaoHolder.getCommonDao().create( joinFragment );
        return joinFragment;
    }

}
