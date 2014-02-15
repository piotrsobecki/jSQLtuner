package pl.piotrsukiennik.ai.id;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public interface Identifier<T extends Comparable> extends Comparable<Identifier> {
    T getValue();
}
