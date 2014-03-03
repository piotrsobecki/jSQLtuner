package pl.piotrsukiennik.tuner.service.impl.transactional;

import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.*;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.persistance.QueryElementDao;
import pl.piotrsukiennik.tuner.service.QueryElementService;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
//@Service
@Transactional("jsqlTunerTransactionManager")
public class QueryElementServiceImpl implements QueryElementService {

    protected QueryElementDao getQueryElementDao() {
        return Dao.getQueryElement();
    }

    public GroupByFragment getOrCreate( Expression element, int position ) {
        GroupByFragment groupByFragment = new GroupByFragment();
        groupByFragment.setExpression( element );
        groupByFragment.setPosition( position );
        return getQueryElementDao().getOrCreateGroupBy( groupByFragment );
    }

    public OperatorExpression getOrCreate( OperatorExpression expression ) {
        if ( expression.getId() != 0 ) {
            return expression;
        }
        return getQueryElementDao().getOrCreate( expression );
    }

    public ColumnValue getOrCreate( ColumnValue columnValue ) {
        if ( columnValue.getId() != 0 ) {
            return columnValue;
        }
        return getQueryElementDao().getOrCreate( columnValue );
    }

    public GroupByFragment getOrCreate( GroupByFragment groupByFragment ) {
        if ( groupByFragment.getId() != 0 ) {
            return groupByFragment;
        }
        return getQueryElementDao().getOrCreateGroupBy( groupByFragment );
    }

    public OrderByFragment getOrCreate( OrderByFragment orderByFragment ) {
        if ( orderByFragment.getId() != 0 ) {
            return orderByFragment;
        }
        return getQueryElementDao().getOrCreate( orderByFragment );
    }

    public Values getOrCreate( Values values ) {
        if ( values.getId() != 0 ) {
            return values;
        }
        return getQueryElementDao().getOrCreate( values );
    }

    public <T extends Projection> T getOrCreate( T projection ) {
        if ( projection.getId() != 0 ) {
            return projection;
        }
        return getQueryElementDao().getOrCreate( projection );
    }

    public <T extends Source> T getOrCreate( T source ) {
        if ( source.getId() != 0 ) {
            return source;
        }
        return getQueryElementDao().getOrCreate( source );
    }

    @Override
    public JoinFragment getOrCreate( JoinFragment joinFragment ) {
        if ( joinFragment.getId() != 0 ) {
            return joinFragment;
        }
        return getQueryElementDao().getOrCreate( joinFragment );
    }

    @Override
    public <E extends Expression> E getOrCreate( E expression ) {
        if ( expression.getId() != 0 ) {
            return expression;
        }
        return getQueryElementDao().getOrCreate( expression );
    }
}
