package pl.piotrsukiennik.tuner.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:14
 */
public class Collections3 {

    private static final Log LOG = LogFactory.getLog( Collections3.class );

    private Collections3() {
    }

    public static <T> Collection<T> merge(Collection<T> targetCollection,Collection<T>... collections){
        for (Collection collection: collections){
            targetCollection.addAll( collection );
        }
        return targetCollection;
    }

    public static <T> T first( Collection<T> collection ) {
        if ( collection != null && !collection.isEmpty() ) {
            return collection.iterator().next();
        }
        return null;
    }

    public static int size(Collection collection){
        if (collection==null){
            return 0;
        }
        return collection.size();
    }
}
