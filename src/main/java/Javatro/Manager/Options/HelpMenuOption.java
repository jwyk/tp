package Javatro.Manager.Options;

import Javatro.Core.JavatroException;
import Javatro.Manager.JavatroManager;
import Javatro.Display.UI;

/**
 * The HelpMenuOption class handles displaying the help screen in Javatro. This command is executed when
 * the player selects the help option.
 */
public class HelpMenuOption implements Option {

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Help Menu";
    }

    /** Executes the command to display the help screen. */
    @Override
    public void execute() throws JavatroException {
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}
