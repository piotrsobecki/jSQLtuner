package pl.piotrsukiennik.tuner.util.hash;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:34
 */
public class HashGenerators {
    private HashGenerators() {
    }

    public static HashGenerator MD5 = new HashGenerator() {
        @Override
        public synchronized String getHash( String value ) {
            return MD5Hash.toMd5( value );
        }
    };


}
