package pl.piotrsukiennik.tuner.model.query.execution;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 27.08.13
 * Time: 20:00
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DataSource extends ValueEntity {
    private String identifier;

    private String clazz;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier( String identifier ) {
        this.identifier = identifier;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz( String clazz ) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof DataSource ) ) {
            return false;
        }
        if ( !super.equals( o ) ) {
            return false;
        }
        DataSource that = (DataSource) o;
        return !( clazz != null ? !clazz.equals( that.clazz ) : that.clazz != null ) && !( identifier != null ? !identifier.equals( that.identifier ) : that.identifier != null );

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ( identifier != null ? identifier.hashCode() : 0 );
        result = 31 * result + ( clazz != null ? clazz.hashCode() : 0 );
        return result;
    }
}
