/**
 * The {@code ReturnOption} class represents a command that allows the player to return to the
 * main game screen after navigating away.
 */
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.CardSelectScreen;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

import static javatro.display.UI.getPreviousScreen;

/** A command that enables players to return to the game screen. */
public class ReturnOption implements Option {

    private Screen prev_screen;

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Return To Previous Screen";
    }

    /**
     * Executes the resume game command, updating the game screen to return the player to the main
     * game interface.
     *
     * @throws JavatroException If an error occurs during execution.
     */
    @Override
    public void execute() throws JavatroException {
        prev_screen = getPreviousScreen();
        // Go back to previous screen
        JavatroManager.setScreen(prev_screen);
    }
}
