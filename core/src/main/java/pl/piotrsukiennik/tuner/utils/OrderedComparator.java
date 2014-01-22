package pl.piotrsukiennik.tuner.utils;

import java.util.Comparator;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:56
 */
public class OrderedComparator implements Comparator<Ordered> {
    @Override
    public int compare( Ordered o1, Ordered o2 ) {
        return Integer.compare( o1.getOrder(), o2.getOrder() );
    }
}
