package pl.piotrsukiennik.tuner.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java program to generate MD5 hash or digest for String. In this example
 * we will see 3 ways to create MD5 hash or digest using standard Java API,
 * Spring framework and open source library, Apache commons codec utilities.
 * Generally MD5 has are represented as Hex String so each of this function
 * will return MD5 hash in hex format.
 *
 * @author Javin Paul
 */
public class MD5Hash {
    private static Logger LOG = Logger.getLogger( MD5Hash.class.getName() );

    public static String toMd5( String message ) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            byte[] hash = md.digest( message.getBytes( "UTF-8" ) );

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder( 2 * hash.length );
            for ( byte b : hash ) {
                sb.append( String.format( "%02x", b & 0xff ) );
            }

            digest = sb.toString();

        }
        catch ( UnsupportedEncodingException ex ) {
            if ( LOG.isLoggable( Level.SEVERE ) ) {
                LOG.log( Level.SEVERE, null, ex );
            }
        }
        catch ( NoSuchAlgorithmException ex ) {
            if ( LOG.isLoggable( Level.SEVERE ) ) {
                LOG.log( Level.SEVERE, null, ex );
            }
        }
        return digest;
    }


}
