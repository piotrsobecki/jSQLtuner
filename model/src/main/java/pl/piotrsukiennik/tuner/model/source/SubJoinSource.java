package pl.piotrsukiennik.tuner.model.source;

import pl.piotrsukiennik.tuner.model.other.JoinFragment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:02
 */
@Entity
public class SubJoinSource extends Source {
    private JoinFragment joinFragment;

    private Source source;

    @ManyToOne(cascade = CascadeType.MERGE)
    public JoinFragment getJoinFragment() {
        return joinFragment;
    }

    public void setJoinFragment( JoinFragment joinFragment ) {
        this.joinFragment = joinFragment;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Source getSource() {
        return source;
    }

    public void setSource( Source source ) {
        this.source = source;
    }
}
