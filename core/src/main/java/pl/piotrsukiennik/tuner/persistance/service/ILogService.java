package pl.piotrsukiennik.tuner.persistance.service;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 14:30
 */
public interface ILogService {
    void logException(String query, String exception);
}
