package pl.piotrsukiennik.tuner.test.unit.entities;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import pl.piotrsukiennik.tuner.test.unit.suite.TestEntities;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
//@RunWith( SpringJUnit4ClassRunner.class )
//@ContextConfiguration( locations = { "/wrapper/jsqltuner-test-context.xml" } )
public class EntitiesTestJsqlTunerWrapper extends TestEntities {
    @BeforeClass
    public static void beforeClass() {
        beforeClassMethod();
    }

    @AfterClass
    public static void afterClass() {
        afterClassMethod();
    }

}
