/**
 * The {@code LoadOptionsScreenOption} class represents a command that loads the options screen,
 * allowing players to adjust game settings.
 */
package Javatro.Manager.Options;

import Javatro.Manager.JavatroManager;
import Javatro.UI.UI;

/** A command that loads the options screen when executed. */
public class LoadOptionsScreenOption implements Option {

    /** Constructs a {@code LoadOptionsScreenOption}. */
    public LoadOptionsScreenOption() {}

    /** Executes the command to change the screen to the options menu. */
    @Override
    public void execute() {
        JavatroManager.setScreen(UI.getOptionScreen());
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
