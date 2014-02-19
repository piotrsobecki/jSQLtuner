package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.source.Source;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface SourcesAwareQuery {
    Set<Source> getSources();

    void setSources( Set<Source> sources );
}
