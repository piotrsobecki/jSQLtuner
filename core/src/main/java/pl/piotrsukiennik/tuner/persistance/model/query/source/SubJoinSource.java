package pl.piotrsukiennik.tuner.persistance.model.query.source;

import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.other.JoinFragment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:02
 */
@Entity
public class SubJoinSource extends Source{
    private JoinFragment joinFragment;
    private Source source;

    @ManyToOne(cascade = CascadeType.ALL)
    public JoinFragment getJoinFragment() {
        return joinFragment;
    }

    public void setJoinFragment(JoinFragment joinFragment) {
        this.joinFragment = joinFragment;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}