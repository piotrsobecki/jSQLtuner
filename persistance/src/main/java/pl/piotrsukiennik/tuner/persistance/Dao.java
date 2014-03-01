package pl.piotrsukiennik.tuner.persistance;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public abstract class Dao {

    protected Dao() {
    }

    protected static LogDao log;

    protected static QueryDao query;

    protected static SchemaDao schema;

    protected static QueryElementDao queryElement;

    public static LogDao getLog() {
        return log;
    }

    public static QueryDao getQuery() {
        return query;
    }

    public static SchemaDao getSchema() {
        return schema;
    }

    protected static void setLog( LogDao logDao ) {
        Dao.log = logDao;
    }

    protected static void setQuery( QueryDao queryDao ) {
        Dao.query = queryDao;
    }

    protected static void setSchema( SchemaDao schemaDao ) {
        Dao.schema = schemaDao;
    }

    public static QueryElementDao getQueryElement() {
        return queryElement;
    }

    protected static void setQueryElement( QueryElementDao queryElementDao ) {
        Dao.queryElement = queryElementDao;
    }
}
