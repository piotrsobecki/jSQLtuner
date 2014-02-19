package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.expression.Expression;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface ProjectionsAware extends SourcesAware {
    Set<Expression> getProjections();

    void setProjections( Set<Expression> projections );
}
