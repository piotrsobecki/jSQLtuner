package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.*;
import pl.piotrsukiennik.tuner.model.source.Source;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface QueryElementDao {

    GroupByFragment getOrCreateGroupBy( GroupByFragment groupByFragment );

    OrderByFragment getOrCreate( OrderByFragment orderByFragment );

    JoinFragment getOrCreate( JoinFragment joinFragment );

    OperatorExpression getOrCreate( OperatorExpression expression );

    ColumnValue getOrCreate( ColumnValue columnValue );

    Values getOrCreate( Values values );

    <T extends Projection> T getOrCreate( T projection );

    <T extends Source> T getOrCreate( T source );

    <E extends Expression> E getOrCreate( E expression );
}
