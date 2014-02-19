package pl.piotrsukiennik.tuner.model.expression;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Table(name = "ExpressionEntity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Expression extends ValueEntity {

}
