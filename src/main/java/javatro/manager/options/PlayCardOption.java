// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

/**
 * Represents a command to play selected cards during gameplay.
 * When executed, transitions to the card selection interface for playing cards.
 */
public class PlayCardOption implements Option {

    /**
     * {@inheritDoc}
     * @return The static description "Play Cards"
     */
    @Override
    public String getDescription() {
        return "Play Cards";
    }

    /**
     * {@inheritDoc}
     * Transitions to the card play selection screen.
     *
     * @throws JavatroException if screen transition fails
     */
    @Override
    public void execute() throws JavatroException {
        // Retrieve and validate play screen
        Screen playScreen = UI.getPlayScreen();
        assert playScreen != null : "Play screen must be initialized";

        // Update display to card play interface
        JavatroManager.setScreen(playScreen);
    }
}