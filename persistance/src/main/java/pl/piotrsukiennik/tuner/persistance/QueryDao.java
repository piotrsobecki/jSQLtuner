package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.other.Values;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.source.Source;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface QueryDao extends CrudDao {
    <T extends Query> T getQueryByHash( String hash );

    <T extends Query> T getQueryByHash( Class<? extends Query> clazz, String hash );

    <T extends Query> T submit( T query );

    void submit( Condition condition );

    void submit( ColumnValue columnValue );

    void submit( GroupByFragment groupByFragment );

    void submit( OrderByFragment orderByFragment );

    void submit( Values values );

    void submit( Projection projection );

    void submit( Source source );

}
