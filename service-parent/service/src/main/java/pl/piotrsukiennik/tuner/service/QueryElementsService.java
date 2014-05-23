package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.*;
import pl.piotrsukiennik.tuner.model.source.Source;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface QueryElementsService {

    OperatorExpression getOrCreate( OperatorExpression expression );

    ColumnValue getOrCreate( ColumnValue columnValue );

    GroupByFragment getOrCreate( GroupByFragment groupByFragment );

    OrderByFragment getOrCreate( OrderByFragment orderByFragment );

    Values getOrCreate( Values values );

    <T extends Projection> T getOrCreate( T projection );

    <T extends Source> T getOrCreate( T source );

    JoinFragment getOrCreate( JoinFragment joinFragment );

    <E extends Expression> E getOrCreate( E expression );
}
