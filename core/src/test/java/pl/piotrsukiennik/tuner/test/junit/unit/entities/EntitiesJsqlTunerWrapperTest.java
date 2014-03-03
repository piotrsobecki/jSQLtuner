package pl.piotrsukiennik.tuner.test.junit.unit.entities;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.junit.TestEntities;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/wrapper/jsqltuner-test-context.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntitiesJsqlTunerWrapperTest extends TestEntities {
}
