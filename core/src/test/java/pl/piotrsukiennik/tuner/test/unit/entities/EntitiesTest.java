package pl.piotrsukiennik.tuner.test.unit.entities;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.unit.suite.TestEntities;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jsqltuner-test-root-plain-context.xml" })
public class EntitiesTest extends TestEntities {
    @BeforeClass
    public static void beforeClass() {
        beforeClassMethod();
    }

    @AfterClass
    public static void afterClass() {
        afterClassMethod();
    }

}
