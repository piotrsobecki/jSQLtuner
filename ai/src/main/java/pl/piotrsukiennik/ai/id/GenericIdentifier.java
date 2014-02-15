package pl.piotrsukiennik.ai.id;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class GenericIdentifier<T extends Comparable> implements Identifier<T> {
    private T value;

    public GenericIdentifier( T value ) {
        this.value = value;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        GenericIdentifier that = (GenericIdentifier) o;
        if ( !value.equals( that.value ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int i = 1;
        if ( value != null ) {
            i += value.hashCode();
        }
        return i;
    }


    @Override
    public int compareTo( Identifier o ) {
        return getValue().compareTo( o.getValue() );
    }

    @Override
    public T getValue() {
        return value;
    }
}
