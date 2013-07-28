package pl.piotrsukiennik.tuner.persistance.model.query.condition;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class Condition extends ValueEntity {


}
