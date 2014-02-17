package pl.piotrsukiennik.ai;


import junit.framework.TestCase;
import pl.piotrsukiennik.ai.id.Identifiers;
import pl.piotrsukiennik.ai.selectable.SimpleSelectable;
import pl.piotrsukiennik.ai.selectionhelper.impl.BestSelection;

/**
 * Unit test for simple App.
 */
public class BestSelectionTest {

    @org.junit.Test
    public void testBestSelection() {
        SimpleSelectable ss1 = new SimpleSelectable( Identifiers.id( "ss1" ), 1 );
        SimpleSelectable ss2 = new SimpleSelectable( Identifiers.id( "ss2" ), 2 );
        SimpleSelectable ss3 = new SimpleSelectable( Identifiers.id( "ss3" ), 3.5 );
        SimpleSelectable ss4 = new SimpleSelectable( Identifiers.id( "ss4" ), -1.0 );
        SimpleSelectable ss5 = new SimpleSelectable( Identifiers.id( "ss5" ), -20.0 );
        BestSelection<SimpleSelectable> bestSelection = new BestSelection<SimpleSelectable>();
        bestSelection.submit( ss1 );
        bestSelection.submit( ss2 );
        bestSelection.submit( ss3 );
        bestSelection.submit( ss4 );
        bestSelection.submit( ss5 );
        TestCase.assertEquals( ss3.getIdentifier(), bestSelection.select().getIdentifier() );
        //TestAddOption
        SimpleSelectable ss6 = new SimpleSelectable( Identifiers.id( "ss6" ), 15.0 );
        bestSelection.submit( ss6 );
        TestCase.assertEquals( ss6.getIdentifier(), bestSelection.select().getIdentifier() );
        //TestRemoveOption
        bestSelection.removeOption( ss6.getIdentifier() );
        TestCase.assertEquals( ss3.getIdentifier(), bestSelection.select().getIdentifier() );
    }
}
