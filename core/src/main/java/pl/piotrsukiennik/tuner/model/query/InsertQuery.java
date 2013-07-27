package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.source.TableSource;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class InsertQuery extends WriteQuery {
    private TableSource tableSource;

    @ManyToOne
    public TableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(TableSource tableSource) {
        this.tableSource = tableSource;
    }
}
