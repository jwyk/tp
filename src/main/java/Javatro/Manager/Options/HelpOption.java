package Javatro.Manager.Options;

import Javatro.Manager.JavatroManager;
import Javatro.UI.UI;

/**
 * The HelpOption class handles displaying the help screen in Javatro. This command is executed when
 * the player selects the help option.
 */
public class HelpOption implements Option {

    public HelpOption() {}

    /** Executes the command to display the help screen. */
    @Override
    public void execute() {
        JavatroManager.setScreen(UI.getHelpScreen());
    }

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Select Help";
    }
}
