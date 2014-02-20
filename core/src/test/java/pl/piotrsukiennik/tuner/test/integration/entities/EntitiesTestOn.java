package pl.piotrsukiennik.tuner.test.integration.entities;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.integration.TestEntities;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "/jsqltuner-test-root-context.xml" } )
public class EntitiesTestOn extends TestEntities {
}
