package pl.piotrsukiennik.tuner.test.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.model.TestEntity;
import pl.piotrsukiennik.tuner.test.service.EntityService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jsqltuner-test-root-context.xml" })
public class HibernateEntitiesTest {

    @Autowired
    private EntityService entityService;

    public static final int SINGLE_GET_RUNS = 100;

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

        List<TestEntity> testEntities = entityService.getTestEntities();
        Assert.assertEquals( 2, testEntities.size() );

        TestEntity t3 = entityService.saveTestEntry( "test3" );

        List<TestEntity> testEntities2 = entityService.getTestEntities();
        Assert.assertEquals( 3, testEntities2.size() );

        entityService.deleteTestEntry( t3 );

        List<TestEntity> testEntities3 = entityService.getTestEntities();
        Assert.assertEquals( 2, testEntities3.size() );

    }

    @Test
    public void testGetSingle() {
        TimeUnit out = TimeUnit.MILLISECONDS;
        long start = System.currentTimeMillis();
        for ( int i = 0; i < SINGLE_GET_RUNS; i++ ) {
            List<TestEntity> testEntities = entityService.getTestEntries( "test1" );
            Assert.assertEquals( 1, testEntities.size() );
            Assert.assertNotNull( testEntities.get( 0 ) );
            Assert.assertNotNull( testEntities.get( 0 ).getId() );
            Assert.assertNotNull( testEntities.get( 0 ).getString() );
        }
        long end = System.currentTimeMillis();
        long diff = end - start;
        long sec = out.convert( diff, TimeUnit.MILLISECONDS );
        System.out.println( "Execution took " + sec + " " + out.name() );
    }


    @Test
    public void testGetNull() {
        TestEntity testEntity2 = entityService.getTestEntry( 9999 );
        Assert.assertNull( testEntity2 );
    }


    @Test
    public void testGetDifferentValues() {
        List<TestEntity> testEntities = entityService.getTestEntries( "test1" );
        TestEntity testEntity = testEntities.get( 0 );
        Assert.assertNotNull( testEntity );
        Assert.assertEquals( "test1", testEntity.getString() );

        List<TestEntity> testEntitiesEmpty = entityService.getTestEntries( "test999" );
        Assert.assertEquals( 0, testEntitiesEmpty.size() );
    }
}
