package pl.piotrsukiennik.ai.utils;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public class Comparables {
    private Comparables() {

    }

    public static <T extends Comparable> int compare( T comparable1, T comparable2 ) {
        if ( comparable1 == null && comparable2 == null ) {
            return 0;
        }
        if ( comparable1 != null && comparable2 == null ) {
            return 1;
        }
        if ( comparable1 == null ) {
            return -1;
        }
        return comparable1.compareTo( comparable2 );
    }
}
