package pl.piotrsukiennik.tuner.test.unit.suite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.model.MockData;
import pl.piotrsukiennik.tuner.test.model.MockDataModel;
import pl.piotrsukiennik.tuner.test.service.EntityService;
import pl.piotrsukiennik.tuner.test.unit.AbstractFrameworkCommon;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTest;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTestJsqlTunerAop;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTestJsqlTunerWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

//import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTestJsqlTunerWrapper;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
 EntitiesTest.class,
 EntitiesTestJsqlTunerAop.class,
 EntitiesTestJsqlTunerWrapper.class
})
public abstract class TestEntities extends AbstractFrameworkCommon {

    @Autowired
    private EntityService entityService;

    public static final int SINGLE_GET_RUNS = 1000;

    @Before
    public void setupBefore() {
        MockData t1 = entityService.save( "test1" );
        MockData t2 = entityService.save( "test2" );
    }

    @After
    public void setupAfter() {
        List<MockDataModel> mockDataList = new ArrayList<>();
        List<MockDataModel> t1 = entityService.getEntriesByEmail( "test1" );
        List<MockDataModel> t2 = entityService.getEntriesByEmail( "test2" );
        mockDataList.addAll( t1 );
        mockDataList.addAll( t2 );
        for ( MockDataModel t : mockDataList ) {
            entityService.deleteTestEntry( t );
        }
    }


    @Test
    public void testCacheClear() {
        test( "testCacheClear()", new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                List<MockDataModel> testEntities = entityService.getTestEntities();
                int size = testEntities.size();

                MockDataModel t3 = entityService.save( "test3" );

                List<MockDataModel> testEntities2 = entityService.getTestEntities();
                Assert.assertEquals( ( size + 1 ), testEntities2.size() );

                entityService.deleteTestEntry( t3 );

                List<MockDataModel> testEntities3 = entityService.getTestEntities();
                Assert.assertEquals( size, testEntities3.size() );
                return null;
            }
        } );
    }


    @Test
    public void getSingle() {
        test( "getSingle()", new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for ( int i = 0; i < SINGLE_GET_RUNS; i++ ) {
                    List<MockDataModel> testEntities = entityService.getEntriesByEmail( "test1" );
                    Assert.assertEquals( 1, testEntities.size() );
                    Assert.assertNotNull( testEntities.get( 0 ) );
                    Assert.assertNotNull( testEntities.get( 0 ).getId() );
                    Assert.assertNotNull( testEntities.get( 0 ).getEmail() );
                }
                return null;
            }
        } );
    }


    @Test
    public void testGetNull() {
        test( "testGetNull()", new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                MockData mockData2 = entityService.getTestEntry( -1 );
                Assert.assertNull( mockData2 );
                return null;
            }
        } );
    }

    @Test
    public void testGetDifferentValues() {
        test( "testGetDifferentValues()", new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                List<MockDataModel> testEntities = entityService.getEntriesByEmail( "test1" );
                MockData mockData = testEntities.get( 0 );
                Assert.assertNotNull( mockData );
                Assert.assertEquals( "test1", mockData.getEmail() );

                List<MockDataModel> testEntitiesEmpty = entityService.getEntriesByEmail( "test999" );
                Assert.assertEquals( 0, testEntitiesEmpty.size() );
                return null;
            }
        } );
    }

}
