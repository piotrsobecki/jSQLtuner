package pl.piotrsukiennik.tuner.test.unit;

import com.carrotsearch.junitbenchmarks.h2.H2Consumer;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Piotr Sukiennik
 * @date 02.03.14
 */
public class H2ConsumerFactory {

    private static File CHARTS_FOLDER;

    private static File DB_FILE_FOLDER;

    private static final String DB_FILE_FOLDER_STR = "target/db/test/";

    private static final String CHARTS_FOLDER_STR = "resources/charts/";

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
            DB_FILE_FOLDER = new File( val + DB_FILE_FOLDER_STR );
            CHARTS_FOLDER = new File( val + CHARTS_FOLDER_STR );

            FileSystemUtils.deleteRecursively( DB_FILE_FOLDER );
            FileSystemUtils.deleteRecursively( CHARTS_FOLDER );

        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    private H2ConsumerFactory() {

    }

    protected static File getDbFile( Class clazz ) {
        String clazzSimple = clazz.getSimpleName();
        return new File( DB_FILE_FOLDER, clazzSimple );
    }

    protected static File getChartsDir( Class clazz ) {
        String clazzSimple = clazz.getSimpleName();
        return new File( CHARTS_FOLDER, clazzSimple );
    }

    public static H2Consumer getH2Consumer( Class clazz ) {
        File dbFile = getDbFile( clazz );
        File chartsDir = getChartsDir( clazz );
        FileSystemUtils.deleteRecursively( dbFile );
        FileSystemUtils.deleteRecursively( chartsDir );
        dbFile.getParentFile().mkdirs();
        chartsDir.getParentFile().mkdirs();
        return new H2Consumer( dbFile, chartsDir, clazz.getSimpleName() );
    }
}
