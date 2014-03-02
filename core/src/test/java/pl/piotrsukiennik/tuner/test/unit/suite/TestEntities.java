package pl.piotrsukiennik.tuner.test.unit.suite;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.test.model.MockData;
import pl.piotrsukiennik.tuner.test.model.MockDataModel;
import pl.piotrsukiennik.tuner.test.service.EntityService;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTest;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTestJsqlTunerAop;
import pl.piotrsukiennik.tuner.test.unit.entities.EntitiesTestJsqlTunerWrapper;

import java.util.ArrayList;
import java.util.List;


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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart
public abstract class TestEntities {

    @Autowired
    private EntityService entityService;


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
        List<MockDataModel> testEntities = entityService.getTestEntities();
        int size = testEntities.size();

        MockDataModel t3 = entityService.save( "test3" );

        List<MockDataModel> testEntities2 = entityService.getTestEntities();
        Assert.assertEquals( ( size + 1 ), testEntities2.size() );

        entityService.deleteTestEntry( t3 );

        List<MockDataModel> testEntities3 = entityService.getTestEntities();
        Assert.assertEquals( size, testEntities3.size() );
    }


    @Test
    @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 0)
    public void testGetNull() {
        MockData mockData2 = entityService.getTestEntry( -1 );
        Assert.assertNull( mockData2 );
    }

    @Test
    public void testGetDifferentValues() {
        List<MockDataModel> testEntities = entityService.getEntriesByEmail( "test1" );
        MockData mockData = testEntities.get( 0 );
        Assert.assertNotNull( mockData );
        Assert.assertEquals( "test1", mockData.getEmail() );

        List<MockDataModel> testEntitiesEmpty = entityService.getEntriesByEmail( "test999" );
        Assert.assertEquals( 0, testEntitiesEmpty.size() );
    }

    public void testSingleSelect( final int runs ) {
        for ( int i = 0; i < runs; i++ ) {
            List<MockDataModel> testEntities = entityService.getEntriesByEmail( "test1" );
            Assert.assertEquals( 1, testEntities.size() );
            Assert.assertNotNull( testEntities.get( 0 ) );
            Assert.assertNotNull( testEntities.get( 0 ).getId() );
            Assert.assertNotNull( testEntities.get( 0 ).getEmail() );
        }
    }

    @Test
    public void testSingleSelect1() {
        testSingleSelect( 1 );
    }

    @Test
    public void testSingleSelect10() {
        testSingleSelect( 10 );
    }

}
