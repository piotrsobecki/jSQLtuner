package pl.piotrsukiennik.tuner.model.schema;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Entity;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@javax.persistence.Table(name = "DatabaseEntity")
public class Database extends ValueEntity {
}
