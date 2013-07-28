package pl.piotrsukiennik.tuner.persistance.model.query.source;

import pl.piotrsukiennik.tuner.persistance.model.schema.Table;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:01
 */
@Entity
public class TableSource extends Source{
    private Table table;

    @ManyToOne
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
