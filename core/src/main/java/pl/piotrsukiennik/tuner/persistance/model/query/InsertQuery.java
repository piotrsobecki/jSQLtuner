package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.*;
import pl.piotrsukiennik.tuner.persistance.model.schema.Column;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class InsertQuery extends WriteQuery {
    private pl.piotrsukiennik.tuner.persistance.model.schema.Table table;

    @ManyToOne(cascade = CascadeType.ALL)
    public pl.piotrsukiennik.tuner.persistance.model.schema.Table getTable() {
        return table;
    }

    public void setTable(pl.piotrsukiennik.tuner.persistance.model.schema.Table table) {
        this.table = table;
    }

    private Set<pl.piotrsukiennik.tuner.persistance.model.schema.Column> columns;


    @OneToMany(cascade = CascadeType.ALL)
    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }
}
