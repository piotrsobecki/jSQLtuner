package pl.piotrsukiennik.tuner.persistance;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 14:30
 */
public interface LogDao {
    void log( String query, String exception );

    void log( String query, Throwable exception );
}
