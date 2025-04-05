// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

/**
 * Represents a menu option to view current poker hand combinations. When executed, transitions the
 * display to the poker hand analysis screen.
 */
public class PokerHandOption implements Option {

    /**
     * {@inheritDoc}
     *
     * @return Constant description "View Poker Hands"
     */
    @Override
    public String getDescription() {
        return "View Poker Hands";
    }

    /**
     * {@inheritDoc} Transitions display to the poker hand analysis interface.
     *
     * @throws JavatroException if screen transition fails
     */
    @Override
    public void execute() throws JavatroException {
        // Retrieve poker hand analysis screen from UI
        final Screen pokerScreen = UI.getPokerHandScreen();
        assert pokerScreen != null : "Poker hand screen must be initialized";

        // Update display to show poker hand analysis
        JavatroManager.setScreen(pokerScreen);
    }
}
