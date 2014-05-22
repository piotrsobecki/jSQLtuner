package pl.piotrsukiennik.tuner.size;

import org.springframework.stereotype.Component;

import javax.sql.RowSet;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Component
public class RowSetSizeEstimatorImpl implements RowSetSizeEstimator {
    public int sizeof( CachedRowSet rowSet ) throws SQLException{
        return rowSet.size() * sizeof( rowSet.getMetaData() );
    }

    @Override
    public int sizeof( ResultSetMetaData metaData ) throws SQLException {
        int count = metaData.getColumnCount();
        int size = 0;
        for (int i=1; i<=count; i++){
            size+=metaData.getPrecision( i );
        }
        return size;
    }
}
