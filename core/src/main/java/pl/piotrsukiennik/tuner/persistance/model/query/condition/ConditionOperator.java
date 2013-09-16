package pl.piotrsukiennik.tuner.persistance.model.query.condition;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.Entity;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:11
 */
public enum ConditionOperator {
    Between,
    EqualsTo,
    Exists,
    List,
    GreaterThan,
    GreaterThanEquals,
    In,
    IsNull,
    ItemsList,
    ItemsListVisitor,
    Like,
    Matches,
    MinorThan,
    MinorThanEquals,
    NotEqualsTo,
    And,
    Or,
    Addition,
    BitwiseAnd,
    BitwiseOr,
    BitwiseXor,
    Concat,
    Division,
    Multiplication,
    Subtraction,
    AllComparison,
    AnyComparison,
    Binary,
    Case,
    DateValue,
    DoubleValue,
    Visitor,
    Function,
    Inverse,
    JdbcParameter,
    LongValue,
    NullValue,
    Parenthesis,
    StringValue,
    TimestampValue,
    TimeValue,
    WhenClause
}
