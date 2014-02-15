package pl.piotrsukiennik.ai.selectable;

import pl.piotrsukiennik.ai.id.Identifier;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public interface Selectable<I extends Identifier> {
    I getIdentifier();

    double getFitness();
}
