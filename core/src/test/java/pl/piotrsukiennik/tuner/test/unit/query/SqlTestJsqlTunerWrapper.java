package pl.piotrsukiennik.tuner.test.unit.query;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import pl.piotrsukiennik.tuner.test.unit.suite.TestQueries;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
//@RunWith( SpringJUnit4ClassRunner.class )
//@ContextConfiguration( locations = { "/wrapper/jsqltuner-test-context.xml" } )
public class SqlTestJsqlTunerWrapper extends TestQueries {
    @BeforeClass
    public static void beforeClass() {
        beforeClassMethod();
    }

    @AfterClass
    public static void afterClass() {
        afterClassMethod();
    }

}
