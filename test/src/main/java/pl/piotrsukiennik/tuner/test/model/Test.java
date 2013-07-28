package pl.piotrsukiennik.tuner.test.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:54
 */
@Entity
public class Test {

    private Integer id;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
