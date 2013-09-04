package pl.piotrsukiennik.tuner.persistance.model.query;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class ReadQuery extends Query {

}
