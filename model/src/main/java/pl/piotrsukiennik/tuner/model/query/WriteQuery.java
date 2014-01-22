package pl.piotrsukiennik.tuner.model.query;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class WriteQuery extends Query {
    private Set<WriteQueryExecution> writeQueryExecutions;

    @OneToMany(cascade = CascadeType.MERGE)
    public Set<WriteQueryExecution> getWriteQueryExecutions() {
        return writeQueryExecutions;
    }

    public void setWriteQueryExecutions( Set<WriteQueryExecution> writeQueryExecutions ) {
        this.writeQueryExecutions = writeQueryExecutions;
    }
}
