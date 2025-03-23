package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

/**
 * The {@code DiscardCardOption} class represents a command that allows the player to discard
 * selected cards during the game. It updates the game screen to display the card selection
 * interface for discarding cards.
 */
public class DiscardCardOption implements Option {

    /**
     * Executes the discard cards command, updating the game screen to the "Select Cards to Discard"
     * interface.
     *
     * @throws JavatroException if an error occurs during execution.
     */
    @Override
    public void execute() throws JavatroException {
        // Update the main screen to show the "Select Cards to Discard" screen
        JavatroManager.setScreen(UI.getDiscardScreen());
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Discard Cards";
    }
}