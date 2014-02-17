package pl.piotrsukiennik.tuner.test.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.ParserService;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jsqltuner-context.xml" })
public class ParserServiceTest {

    @Resource
    private ParserService parserService;

    @Test
    @Repeat(2)
    public void testParseSelectQuery() throws QueryParsingNotSupportedException {
        String database = "testdb";
        String schema = "testschema";
        String query = "Select * from Test";

        Query queryParsed = parserService.parse( database, schema, query );

        Assert.assertNotNull( queryParsed );
        Assert.assertTrue( queryParsed instanceof SelectQuery );

        Source source = ( (SelectQuery) queryParsed ).getSources().iterator().next();

        Assert.assertNotNull( source );
        Assert.assertTrue( source instanceof TableSource );

        Assert.assertTrue( "Test".equalsIgnoreCase( ( (TableSource) source ).getTable().getValue() ) );

        Projection projection = ( (SelectQuery) queryParsed ).getProjections().iterator().next();

        Assert.assertNotNull( projection );
        Assert.assertTrue( projection instanceof StarProjection );

        Source projectionSource = ( (StarProjection) projection ).getSource();
        Assert.assertSame( source, projectionSource );
    }


}
