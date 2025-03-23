package Javatro.Manager.Options;

import Javatro.Core.JavatroException;
import Javatro.Manager.JavatroManager;
import Javatro.Display.UI;

/**
 * The HelpRulesOption class provides the rules for playing Javatro. This command is executed
 * when the player requests game rules.
 */
public class HelpRulesOption implements Option {

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Game Rules";
    }

    /** Executes the command to display the rules of the game. */
    @Override
    public void execute() throws JavatroException {
        String title = "♥️ ♠️ 🃏 " + UI.BOLD + "Javatro Rules" + UI.END + " 🃏 ♦️ ♣️";

        String[] lines = {
                "1. Start with a basic deck of cards.",
                "2. Each turn, draw cards and form poker hands to attack or defend.",
                "3. Earn new cards after each battle and upgrade your deck.",
                "4. If your health reaches zero, the run ends—try again with a new strategy!",
                "5. Different enemies and challenges appear in each run.",
                "",
                "Remember: Careful deck management is key to survival!"
        };

        UI.printBorderedMessage(title, lines);
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}