package pl.piotrsukiennik.tuner.test.unit.suite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import pl.piotrsukiennik.tuner.test.model.TestEntity;
import pl.piotrsukiennik.tuner.test.service.EntityService;
import pl.piotrsukiennik.tuner.test.unit.AbstractFrameworkCommon;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTest;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTestJsqlTunerAop;

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
 //EntitiesTestJsqlTunerWrapper.class
})
public abstract class TestEntities extends AbstractFrameworkCommon {

    @Autowired
    private EntityService entityService;

    public static final int SINGLE_GET_RUNS = 1000;

    @Before
    public void setupBefore() {
        TestEntity t1 = entityService.saveTestEntry( "test1" );
        TestEntity t2 = entityService.saveTestEntry( "test2" );
    }

    @After
    public void setupAfter() {
        List<TestEntity> t1 = entityService.getTestEntities();
        for ( TestEntity t : t1 ) {
            entityService.deleteTestEntry( t );
        }
    }


    @Test
    @Repeat(10)
    public void testCacheClear() {
        test( "testCacheClear()", new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                List<TestEntity> testEntities = entityService.getTestEntities();
                Assert.assertEquals( 2, testEntities.size() );

                TestEntity t3 = entityService.saveTestEntry( "test3" );

                List<TestEntity> testEntities2 = entityService.getTestEntities();
                Assert.assertEquals( 3, testEntities2.size() );

                entityService.deleteTestEntry( t3 );

                List<TestEntity> testEntities3 = entityService.getTestEntities();
                Assert.assertEquals( 2, testEntities3.size() );
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
                    List<TestEntity> testEntities = entityService.getTestEntries( "test1" );
                    Assert.assertEquals( 1, testEntities.size() );
                    Assert.assertNotNull( testEntities.get( 0 ) );
                    Assert.assertNotNull( testEntities.get( 0 ).getId() );
                    Assert.assertNotNull( testEntities.get( 0 ).getString() );
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
                TestEntity testEntity2 = entityService.getTestEntry( 9999 );
                Assert.assertNull( testEntity2 );
                return null;
            }
        } );
    }

    @Test
    public void testGetDifferentValues() {
        test( "testGetDifferentValues()", new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                List<TestEntity> testEntities = entityService.getTestEntries( "test1" );
                TestEntity testEntity = testEntities.get( 0 );
                Assert.assertNotNull( testEntity );
                Assert.assertEquals( "test1", testEntity.getString() );

                List<TestEntity> testEntitiesEmpty = entityService.getTestEntries( "test999" );
                Assert.assertEquals( 0, testEntitiesEmpty.size() );
                return null;
            }
        } );
    }

}
