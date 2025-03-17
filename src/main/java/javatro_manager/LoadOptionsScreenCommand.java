/**
 * The {@code LoadOptionsScreenCommand} class represents a command that loads
 * the options screen, allowing players to adjust game settings.
 */
package javatro_manager;

import javatro_view.JavatroView;

/**
 * A command that loads the options screen when executed.
 */
public class LoadOptionsScreenCommand implements Command {

    /**
     * Constructs a {@code LoadOptionsScreenCommand}.
     */
    public LoadOptionsScreenCommand() {}

    /**
     * Executes the command to change the screen to the options menu.
     */
    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroView.getOptionScreen());
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Select Options";
    }
}
