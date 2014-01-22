package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.ValueEntity;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface CrudDao {
    <T extends ValueEntity> T create( T t );

    <T extends ValueEntity> T read( Long id );

    <T extends ValueEntity> T update( T t );

    <T extends ValueEntity> void delete( T t );
}
