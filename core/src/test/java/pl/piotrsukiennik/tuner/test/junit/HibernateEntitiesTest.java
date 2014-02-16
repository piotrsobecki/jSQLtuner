package pl.piotrsukiennik.tuner.test.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.model.TestEntity;
import pl.piotrsukiennik.tuner.test.service.EntityService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jsqltuner-test-root-context.xml" })
public class HibernateEntitiesTest {


    private
    @Resource
    EntityService entityService;


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
        for ( int i = 0; i < 10; i++ ) {
            List<TestEntity> testEntities = entityService.getTestEntries( "test1" );
            Assert.assertEquals( 1, testEntities.size() );
            Assert.assertNotNull( testEntities.get( 0 ) );
            Assert.assertNotNull( testEntities.get( 0 ).getId() );
            Assert.assertNotNull( testEntities.get( 0 ).getString() );
        }

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
