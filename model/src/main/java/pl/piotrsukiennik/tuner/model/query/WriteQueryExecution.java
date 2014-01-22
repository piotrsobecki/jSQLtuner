package pl.piotrsukiennik.tuner.model.query;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 15.09.13
 * Time: 13:50
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class WriteQueryExecution extends QueryExecution {

    private long rowsAffected;

    public long getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected( long rowsAffected ) {
        this.rowsAffected = rowsAffected;
    }
}
