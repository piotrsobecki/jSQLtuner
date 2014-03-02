package pl.piotrsukiennik.tuner.test.unit.entities;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.h2.H2Consumer;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.unit.H2ConsumerFactory;
import pl.piotrsukiennik.tuner.test.unit.TestEntities;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jsqltuner-test-root-plain-context.xml" })
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntitiesTest extends TestEntities {

    protected static final H2Consumer H2_CONSUMER = H2ConsumerFactory.getH2Consumer( EntitiesTest.class );

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule( H2_CONSUMER );
    
}
