// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

/**
 * Represents a command to discard selected cards during gameplay.
 * When executed, transitions to the card selection interface for discarding cards.
 */
public class DiscardCardOption implements Option {

    /**
     * {@inheritDoc}
     * @return The static description "Discard Cards"
     */
    @Override
    public String getDescription() {
        return "Discard Cards";
    }

    /**
     * {@inheritDoc}
     * Transitions to the card discard selection screen.
     *
     * @throws JavatroException if screen transition fails
     */
    @Override
    public void execute() throws JavatroException {
        // Retrieve and validate discard screen
        Screen discardScreen = UI.getDiscardScreen();
        assert discardScreen != null : "Discard screen must be initialized";

        // Update display to discard interface
        JavatroManager.setScreen(discardScreen);
    }
}