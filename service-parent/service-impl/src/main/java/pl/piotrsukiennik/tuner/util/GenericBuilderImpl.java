package pl.piotrsukiennik.tuner.util;

/**
 * @author Piotr Sukiennik
 * @date 25.04.14
 */
public class GenericBuilderImpl<T> implements GenericBuilder<T> {

    private Class<T> buildable;

    public GenericBuilderImpl( Class<T> buildable ) {
        this.buildable = buildable;
    }

    @Override
    public T build(){
        return Objects2.newInstance( buildable );
    }
}
