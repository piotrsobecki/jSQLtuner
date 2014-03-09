package pl.piotrsukiennik.tuner.test.service;

import pl.piotrsukiennik.tuner.test.model.MockData;
import pl.piotrsukiennik.tuner.test.model.MockDataModel;

import java.util.List;

/**
 * @author Piotr Sukiennik
 * @date 09.03.14
 */
public interface EntityService {
    void deleteTestEntry( MockDataModel mockData );

    MockDataModel save( MockData mockData );

    MockDataModel save( String email );

    List<MockDataModel> getEntriesByEmail( String email );

    MockDataModel getTestEntry( Integer id );

    List<MockDataModel> getTestEntities();
}
