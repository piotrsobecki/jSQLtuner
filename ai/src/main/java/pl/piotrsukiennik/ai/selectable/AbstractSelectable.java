package pl.piotrsukiennik.ai.selectable;

import pl.piotrsukiennik.ai.id.Identifier;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public abstract class AbstractSelectable<I extends Identifier> implements Selectable<I> {


    private I identifier;


    protected AbstractSelectable( I identifier ) {
        this.identifier = identifier;
    }

    public I getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        AbstractSelectable that = (AbstractSelectable) o;

        if ( !identifier.equals( that.identifier ) ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
