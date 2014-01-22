package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface JoinsAware {
    Set<JoinFragment> getJoins();

    void setJoins( Set<JoinFragment> joins );
}
