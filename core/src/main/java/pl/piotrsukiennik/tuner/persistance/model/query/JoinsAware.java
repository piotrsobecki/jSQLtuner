package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface JoinsAware {
    Set<JoinFragment> getJoins();
    void setJoins(Set<JoinFragment> joins);
}
