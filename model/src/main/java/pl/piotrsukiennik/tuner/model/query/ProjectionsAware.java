package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.projection.Projection;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface ProjectionsAware extends SourcesAware {
    Set<Projection> getProjections();

    void setProjections( Set<Projection> projections );
}
