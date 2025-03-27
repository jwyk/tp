//@@author swethacool
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

/**
 * The HelpMenuOption class handles displaying the help screen in javatro. This command is executed
 * when the player selects the help option.
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
