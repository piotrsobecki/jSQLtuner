package pl.piotrsukiennik.tuner.test.unit;

import com.carrotsearch.junitbenchmarks.h2.H2Consumer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Piotr Sukiennik
 * @date 02.03.14
 */
public class H2ConsumerFactory {

    private static String DB_FILE_FOLDER = "target/db/test/";

    private static String CHARTS_FOLDER = "resources/charts/";

    private static final String PROP_KEY_BUILD_DIR = "jsqltuner.build.directory";

    private static final String PROP_KEY_BUILD_DIR_DEFAULT = "jsqltuner.build.directory.default";

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = H2ConsumerFactory.class.getResourceAsStream( "/jsqltuner-test.properties" );
            properties.load( inputStream );
            String val = properties.getProperty( PROP_KEY_BUILD_DIR );
            if ( val.startsWith( "$" ) ) {
                val = properties.getProperty( PROP_KEY_BUILD_DIR_DEFAULT );
            }
            DB_FILE_FOLDER = val + DB_FILE_FOLDER;
            CHARTS_FOLDER = val + CHARTS_FOLDER;
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    private H2ConsumerFactory() {

    }

    protected static File getDbFile( Class clazz ) {
        String clazzSimple = clazz.getSimpleName();
        return new File( DB_FILE_FOLDER + clazzSimple );
    }

    protected static File getChartsDir( Class clazz ) {
        String clazzSimple = clazz.getSimpleName();
        return new File( CHARTS_FOLDER + clazzSimple );
    }

    public static H2Consumer getH2Consumer( Class clazz ) {
        File dbFile = getDbFile( clazz );
        File chartsDir = getChartsDir( clazz );
        if ( chartsDir.exists() ) {
            chartsDir.delete();
        }
        if ( dbFile.exists() ) {
            dbFile.delete();
        }
        dbFile.getParentFile().mkdirs();
        chartsDir.getParentFile().mkdirs();
        return new H2Consumer( dbFile, chartsDir, clazz.getSimpleName() );
    }
}
