package pl.piotrsukiennik.tuner.service.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 08.09.13
 * Time: 00:31
 */
public class CollectionUtils {

    public static <T> Set<T> createNewLinkedHashSetIfNull( Set<T> in ) {
        if ( in == null ) {
            return new LinkedHashSet<T>();
        }
        return in;
    }

}
