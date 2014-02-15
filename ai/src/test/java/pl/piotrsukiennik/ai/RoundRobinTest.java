package pl.piotrsukiennik.ai;


import junit.framework.TestCase;
import pl.piotrsukiennik.ai.id.Identifiers;
import pl.piotrsukiennik.ai.selectable.SimpleSelectable;
import pl.piotrsukiennik.ai.selectionhelper.RoundRobinSelection;

/**
 * Unit test for simple App.
 */
public class RoundRobinTest {

    @org.junit.Test
    public void testRoundRobin() {
        SimpleSelectable ss1 = new SimpleSelectable( Identifiers.id( "ss1" ), 1 );
        SimpleSelectable ss2 = new SimpleSelectable( Identifiers.id( "ss2" ), 2 );
        SimpleSelectable ss3 = new SimpleSelectable( Identifiers.id( "ss3" ), 3.5 );
        SimpleSelectable ss4 = new SimpleSelectable( Identifiers.id( "ss4" ), -1.0 );
        SimpleSelectable ss5 = new SimpleSelectable( Identifiers.id( "ss5" ), -20.0 );
        RoundRobinSelection<SimpleSelectable> roundRobinSelection = new RoundRobinSelection<SimpleSelectable>();
        roundRobinSelection.submit( ss1 );
        roundRobinSelection.submit( ss2 );
        roundRobinSelection.submit( ss3 );
        roundRobinSelection.submit( ss4 );
        roundRobinSelection.submit( ss5 );
        TestCase.assertEquals( ss1.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss2.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss3.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss4.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss5.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss1.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        SimpleSelectable ss6 = new SimpleSelectable( Identifiers.id( "ss6" ), 15.0 );
        roundRobinSelection.submit( ss6 );
        TestCase.assertEquals( ss1.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss2.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss3.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss4.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss5.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss6.getIdentifier(), roundRobinSelection.select().getIdentifier() );
        TestCase.assertEquals( ss1.getIdentifier(), roundRobinSelection.select().getIdentifier() );

    }


}
