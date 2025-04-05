// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

/**
 * Represents a menu option to view the current deck composition. When executed, transitions the
 * display to the deck viewing screen.
 */
public class DeckViewOption implements Option {

    /**
     * {@inheritDoc}
     *
     * @return The static description "View Deck"
     */
    @Override
    public String getDescription() {
        return "View Deck";
    }

    /**
     * {@inheritDoc} Transitions the display to show deck contents.
     *
     * @throws JavatroException if screen transition fails
     */
    @Override
    public void execute() throws JavatroException {
        // Retrieve and validate deck view screen
        Screen deckScreen = UI.getDeckViewScreen();
        assert deckScreen != null : "Deck view screen must be initialized";

        // Update display to deck view
        JavatroManager.setScreen(deckScreen);
    }
}
