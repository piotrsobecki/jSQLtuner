package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.expression.Expression;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface ProjectionsAwareQuery extends SourcesAwareQuery {
    Set<Expression> getProjections();

    void setProjections( Set<Expression> projections );
}
