package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.cache.QueryInvalidation;
import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:54
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Query extends ValueEntity implements ValueQuery, QueryInvalidation {

    private String hash;

    protected String queryString;

    @Column(length = 32)
    public String getHash() {
        return hash;
    }

    public void setHash( String hash ) {
        this.hash = hash;
    }

    @Lob
    @Column
    public String getQueryString() {
        return queryString;
    }

    public void setQueryString( String queryString ) {
        this.queryString = queryString;
    }

    @Override
    public void setValue( String value ) {
        this.queryString=value;
        super.setValue( value );
    }
}
