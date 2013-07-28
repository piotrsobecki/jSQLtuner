package pl.piotrsukiennik.tuner.persistance.model;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:15
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ValueEntity {
    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
