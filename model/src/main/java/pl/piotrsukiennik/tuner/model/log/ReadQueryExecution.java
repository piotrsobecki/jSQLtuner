package pl.piotrsukiennik.tuner.model.log;


import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 15.09.13
 * Time: 13:50
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ReadQueryExecution extends QueryExecution {
    private Set<ReadQueryExecution> readQueryExecutions;

    @OneToMany(cascade = CascadeType.MERGE)
    public Set<ReadQueryExecution> getReadQueryExecutions() {
        return readQueryExecutions;
    }

    public void setReadQueryExecutions( Set<ReadQueryExecution> readQueryExecutions ) {
        this.readQueryExecutions = readQueryExecutions;
    }
}
