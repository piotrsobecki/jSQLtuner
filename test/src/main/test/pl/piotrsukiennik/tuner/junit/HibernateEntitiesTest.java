package pl.piotrsukiennik.tuner.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.test.service.EntityService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "/root-context.xml" } )
public class HibernateEntitiesTest {


    private
    @Resource
    EntityService entityService;

    @Test
    @Repeat( 10 )
    public void testGetAll() {
        List<pl.piotrsukiennik.tuner.test.model.Test> testEntities = entityService.testEntities();
        Assert.assertEquals( 3, testEntities.size() );
    }

    @Test
    @Repeat( 10 )
    public void testGetSingle() {
        pl.piotrsukiennik.tuner.test.model.Test testEntity = entityService.testEntry();
        Assert.assertNotNull( testEntity );
        Assert.assertNotNull( testEntity.getId() );
        Assert.assertNotNull( testEntity.getString() );
    }

}
