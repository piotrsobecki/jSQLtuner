package pl.piotrsukiennik.tuner.persistance.impl;

import org.hibernate.Session;
import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.CrudDao;

import java.io.Serializable;


/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
abstract class CrudDaoImpl extends AbstractDaoImpl implements CrudDao {


    @Override
    public <T extends ValueEntity> T create( T t ) {
        Session session = s();
        try {
            Serializable serializable = session.save( t );
            t.setId( (Long) serializable );
            session.flush();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public <T extends ValueEntity> T read( Long id ) {
        throw new RuntimeException( "Read(id) NOT SUPPORTED" );
    }

    @Override
    public <T extends ValueEntity> T update( T t ) {
        Session session = s();
        T tmerged = (T) session.merge( t );
        session.flush();
        session.refresh( tmerged );
        return tmerged;
    }

    @Override
    public <T extends ValueEntity> void delete( T t ) {
        Session session = s();
        session.delete( t );
        session.flush();
    }
}
