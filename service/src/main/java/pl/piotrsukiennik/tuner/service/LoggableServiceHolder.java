package pl.piotrsukiennik.tuner.service;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public abstract class LoggableServiceHolder implements LoggableService {
    private static LoggableService logService;

    public static LoggableService get() {
        return logService;
    }

    protected static void setLogService( LoggableServiceHolder logService ) {
        LoggableServiceHolder.logService = logService;
    }

}
