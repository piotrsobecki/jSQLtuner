package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface ProjectionsAware extends SourcesAware {
    Set<Projection> getProjections();
    void setProjections(Set<Projection> projections);
}
