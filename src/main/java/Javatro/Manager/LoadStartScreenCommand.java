/**
 * The {@code LoadStartScreenCommand} class represents a command that loads the start screen,
 * allowing players to navigate to the main menu.
 */
package Javatro.Manager;

import Javatro.View.JavatroView;

/** A command that loads the main menu (start screen) when executed. */
public class LoadStartScreenCommand implements Command {

    /** Constructs a {@code LoadStartScreenCommand}. */
    public LoadStartScreenCommand() {}

    /** Executes the command to change the screen to the start menu. */
    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroView.getStartScreen());
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Main Menu";
    }
}
