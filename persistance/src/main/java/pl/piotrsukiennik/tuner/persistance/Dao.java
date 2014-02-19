package pl.piotrsukiennik.tuner.persistance;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public abstract class Dao {

    protected Dao() {

    }

    protected static CommonDao commonDao;

    protected static LogDao logDao;

    protected static QueryDao queryDao;

    protected static SchemaDao schemaDao;

    public static LogDao getLog() {
        return logDao;
    }

    public static CommonDao getCommon() {
        return commonDao;
    }

    public static QueryDao getQuery() {
        return queryDao;
    }

    public static SchemaDao getSchema() {
        return schemaDao;
    }

    protected static void setLog( LogDao logDao ) {
        Dao.logDao = logDao;
    }

    protected static void setQuery( QueryDao queryDao ) {
        Dao.queryDao = queryDao;
    }

    protected static void setSchema( SchemaDao schemaDao ) {
        Dao.schemaDao = schemaDao;
    }

    protected static void setCommon( CommonDao commonDao ) {
        Dao.commonDao = commonDao;
    }

}
