package pl.piotrsukiennik.tuner.util;

/**
 * @author Piotr Sukiennik
 * @date 25.04.14
 */
public class GenericBuilderImpl<T> implements GenericBuilder<T> {

    private Class<? extends T> buildable;

    public GenericBuilderImpl( Class<? extends T> buildable ) {
        this.buildable = buildable;
    }

    @Override
    public  T build(){
        return Objects2.newInstance( buildable );
    }
}
