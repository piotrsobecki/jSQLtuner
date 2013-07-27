package pl.piotrsukiennik.tuner.model.log;

import pl.piotrsukiennik.tuner.model.ValueEntity;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:17
 */
public class QueryException extends ValueEntity {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
