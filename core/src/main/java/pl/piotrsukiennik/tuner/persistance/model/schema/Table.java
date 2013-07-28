package pl.piotrsukiennik.tuner.persistance.model.schema;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
public class Table extends ValueEntity {


     private Set<Column> columns;

    @ManyToMany
    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }
}
