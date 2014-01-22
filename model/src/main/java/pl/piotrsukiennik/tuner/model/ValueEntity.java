package pl.piotrsukiennik.tuner.model;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:15
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ValueEntity {
    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    @PrePersist
    @PreUpdate
    protected final void initValue() {
        if ( value == null ) {
            value = this.toString();
        }
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( !( o instanceof ValueEntity ) )
            return false;
        ValueEntity that = (ValueEntity) o;

        if ( id != that.getId() || ( id == 0 && that.getId() == 0 ) )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) ( id ^ ( id >>> 32 ) );
        result = 31 * result + ( value != null ? value.hashCode() : 0 );
        return result;
    }
}
