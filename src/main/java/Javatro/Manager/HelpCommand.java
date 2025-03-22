package Javatro.Manager;

import Javatro.View.JavatroView;

/**
 * The HelpCommand class handles displaying the help screen in Javatro. This command is executed
 * when the player selects the help option.
 */
public class HelpCommand implements Command {

    public HelpCommand() {}

    /** Executes the command to display the help screen. */
    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroView.getHelpScreen());
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
