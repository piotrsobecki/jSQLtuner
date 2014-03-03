package pl.piotrsukiennik.tuner.test.junit.benchmark.query;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.h2.H2Consumer;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.junit.benchmark.H2ConsumerFactory;
import pl.piotrsukiennik.tuner.test.junit.unit.query.SqlTest;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AxisRange(min = 0, max = 1)
@BenchmarkMethodChart
public class SqlBenchmark extends SqlTest {

    protected static final H2Consumer H2_CONSUMER = H2ConsumerFactory.getH2Consumer( SqlBenchmark.class );

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule( H2_CONSUMER );
}
