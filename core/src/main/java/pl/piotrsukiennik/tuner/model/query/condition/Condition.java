package pl.piotrsukiennik.tuner.model.query.condition;

import pl.piotrsukiennik.tuner.model.ValueEntity;

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
