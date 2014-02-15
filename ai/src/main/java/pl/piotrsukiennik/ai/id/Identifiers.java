package pl.piotrsukiennik.ai.id;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class Identifiers {
    public static <T extends Comparable> Identifier<T> id( T t ) {
        return new GenericIdentifier<T>( t );
    }

    private Identifiers() {

    }
}
