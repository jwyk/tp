/**
 * The {@code LoadGameScreenCommand} class represents a command that initializes and starts a new
 * game session by setting the game screen.
 */
package Javatro.Manager;

import Javatro.Exception.JavatroException;
import Javatro.View.JavatroView;

/** A command that starts the game and loads the game screen. */
public class LoadGameScreenCommand implements Command {

    /** Constructs a {@code LoadGameScreenCommand}. */
    public LoadGameScreenCommand() {}

    /**
     * Executes the command to begin the game, restore available commands, and update the screen to
     * display the game interface.
     *
     * @throws JavatroException If an error occurs while starting the game.
     */
    @Override
    public void execute() throws JavatroException {
        JavatroManager.beginGame();
        JavatroView.getGameScreen().restoreGameCommands();

        // Update the main screen to show the game screen
        JavatroManager.setScreen(JavatroView.getGameScreen());
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Start Game";
    }
}
