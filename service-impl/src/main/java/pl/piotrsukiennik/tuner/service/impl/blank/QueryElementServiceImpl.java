package pl.piotrsukiennik.tuner.service.impl.blank;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.*;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.service.QueryElementService;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
@Service
public class QueryElementServiceImpl implements QueryElementService {

    public OperatorExpression getOrCreate( OperatorExpression expression ) {
        return expression;
    }

    public ColumnValue getOrCreate( ColumnValue columnValue ) {
        return columnValue;
    }

    public GroupByFragment getOrCreate( GroupByFragment groupByFragment ) {
        return groupByFragment;
    }

    public OrderByFragment getOrCreate( OrderByFragment orderByFragment ) {
        return orderByFragment;
    }

    public Values getOrCreate( Values values ) {
        return values;
    }

    public <T extends Projection> T getOrCreate( T projection ) {
        return projection;
    }

    public <T extends Source> T getOrCreate( T source ) {
        return source;
    }

    @Override
    public JoinFragment getOrCreate( JoinFragment joinFragment ) {
        return joinFragment;
    }

    @Override
    public <E extends Expression> E getOrCreate( E expression ) {
        return expression;
    }
}
