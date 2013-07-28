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
public class Schema extends ValueEntity {




    private Set<Table> tables;

    @OneToMany
    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }
}
