package pl.piotrsukiennik.tuner.test.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Piotr
 * Date: 28.07.13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    public static interface StringProcessor {
        void process( String str );
    }


    public static void processEachLine( String classPathFile, StringProcessor doWithEachLine ) {
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream( classPathFile );
        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
        BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
        String line = null;
        try {
            while ( ( line = bufferedReader.readLine() ) != null ) {
                doWithEachLine.process( line );
            }
            bufferedReader.close();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

    }
}
