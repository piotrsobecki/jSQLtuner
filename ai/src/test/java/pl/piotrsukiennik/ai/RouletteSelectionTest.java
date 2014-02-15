package pl.piotrsukiennik.ai;


import junit.framework.TestCase;
import pl.piotrsukiennik.ai.id.Identifiers;
import pl.piotrsukiennik.ai.selectable.SimpleSelectable;
import pl.piotrsukiennik.ai.selectionhelper.RouletteWheelSelection;

/**
 * Unit test for simple App.
 */
public class RouletteSelectionTest {


    @org.junit.Test
    public void testBestSelection() {
        SimpleSelectable ss1 = new SimpleSelectable( Identifiers.id( "ss1" ), 0.0001 );
        SimpleSelectable ss2 = new SimpleSelectable( Identifiers.id( "ss2" ), 0.0001 );
        SimpleSelectable ss3 = new SimpleSelectable( Identifiers.id( "ss3" ), 99.9999 );
        SimpleSelectable ss4 = new SimpleSelectable( Identifiers.id( "ss4" ), 0.0001 );
        SimpleSelectable ss5 = new SimpleSelectable( Identifiers.id( "ss5" ), 0.0001 );
        RouletteWheelSelection<SimpleSelectable> wheelSelection = new RouletteWheelSelection<SimpleSelectable>();
        wheelSelection.submit( ss1 );
        wheelSelection.submit( ss2 );
        wheelSelection.submit( ss3 );
        wheelSelection.submit( ss4 );
        wheelSelection.submit( ss5 );
        TestCase.assertEquals( ss3.getIdentifier(), wheelSelection.select().getIdentifier() );
    }
}
