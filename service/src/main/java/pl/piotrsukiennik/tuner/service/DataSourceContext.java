package pl.piotrsukiennik.tuner.service;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface DataSourceContext {
    boolean contains( String database, String schema );
}
