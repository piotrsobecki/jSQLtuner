package pl.piotrsukiennik.tuner.service.impl;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.PairExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.parser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.service.parser.element.FromItemParser;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 18:07
 */
@Service
@Transactional( value = "jsqlTunerTransactionManager" )
public class ParserElementServiceImpl implements ElementParserService {


    public PairExpression getCondition( QueryContext queryContext, OperatorExpression leftExpression, OperatorExpression rightExpression ) {
        PairExpression pairCondition = new PairExpression();
        pairCondition.setLeftExpression( leftExpression );
        pairCondition.setRightExpression( rightExpression );
        Dao.getCommon().create( pairCondition );
        return pairCondition;
    }


    @Override
    public TableSource getTableSource( QueryContext queryContext, Table tableName ) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias( NewQueryUtils.getTableAlias( tableName.getAlias() ) );
        tableSource.setValue( NewQueryUtils.getTableSourceValue( tableName ) );
        tableSource.setTable( queryContext.getTable( tableName.getWholeTableName() ) );
        tableSource = queryContext.mergeTableSource( tableSource );
        if ( tableSource.getId() == 0 ) {
            Dao.getCommon().create( tableSource );
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
        orderByFragment.setOrderByExpression( orderByExpresionParser.getExpression() );
        Dao.getCommon().create( orderByFragment );
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
        Dao.getCommon().create( columnProjection );
        return columnProjection;
    }

    @Override
    public StarProjection getStarProjection( QueryContext queryContext, AllColumns allColumns, Source source ) {
        StarProjection columnProjection = new StarProjection();
        columnProjection.setSource( source );
        Dao.getCommon().create( columnProjection );
        return columnProjection;
    }

    @Override
    public StarProjection getStarProjection( QueryContext queryContext, AllTableColumns allColumns ) {
        TableSource tableSource = null;
        if ( allColumns.getTable().getName() == null ) {
            tableSource = queryContext.getLastAttachedTableSource();
        }
        else {
            tableSource = getTableSource( queryContext, allColumns.getTable() );
        }
        if ( tableSource == null ) {
            throw new RuntimeException( "TableSource cannot be obtained for column." );
        }
        StarProjection columnProjection = new StarProjection();
        columnProjection.setSource( tableSource );
        Dao.getCommon().create( columnProjection );
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
        joinFragment.setOn( parser.getExpression() );
        Dao.getCommon().create( joinFragment );
        return joinFragment;
    }

}
