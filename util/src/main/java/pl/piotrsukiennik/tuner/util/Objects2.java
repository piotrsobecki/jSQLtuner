package pl.piotrsukiennik.tuner.util;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:14
 */
public class Objects2 {


    private Objects2() {
    }

    public static boolean eq( Object o1, Object o2 ) {
        return ( o1 == null && o2 == null ) || ( o1 != null && o2 != null && o1.equals( o2 ) );
    }

    public static <T> T newInstance( Class<T> clazz ) {
        try {
            return clazz.newInstance();
        }
        catch ( IllegalAccessException | InstantiationException ie ) {
            throw new NewInstanceException( ie );
        }
    }
}
