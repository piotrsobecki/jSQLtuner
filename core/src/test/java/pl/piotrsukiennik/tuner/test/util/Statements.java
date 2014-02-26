package pl.piotrsukiennik.tuner.test.util;


import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor;
import org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor;
import org.hibernate.tool.hbm2ddl.SingleLineSqlCommandExtractor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: Piotr
 * Date: 28.07.13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class Statements {

    public static interface Processor<T> {
        void call( T o );
    }

    public static interface StringProcessor extends Processor<String> {
        void call( String str );
    }


    public static void forEachValid( ClassLoader classLoader, String classPathFile, Processor<String> doWithEachLine ) {
        InputStream inputStream = classLoader.getResourceAsStream( classPathFile );
        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
        forEach( inputStreamReader, new MultipleLinesSqlCommandExtractor(), doWithEachLine );
    }

    public static void forEachInValid( ClassLoader classLoader, String classPathFile, Processor<String> doWithEachLine ) {
        InputStream inputStream = classLoader.getResourceAsStream( classPathFile );
        InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
        forEach( inputStreamReader, new SingleLineSqlCommandExtractor(), doWithEachLine );
    }

    public static void forEach( Reader reader, ImportSqlCommandExtractor extractor, Processor<String> doWithEachLine ) {
        String[] statements = extractor.extractCommands( reader );
        for ( String sql : statements ) {
            doWithEachLine.call( sql );
        }
    }
}
