package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.other.JoinFragment;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:30
 */
public interface JoinsAwareQuery {
    Set<JoinFragment> getJoins();

    void setJoins( Set<JoinFragment> joins );
}
