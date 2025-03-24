package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;

/**
 * The HelpRulesOption class provides the rules for playing javatro. This command is executed when
 * the player requests game rules.
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
        String title = "♥️ ♠️ 🃏 " + UI.BOLD + "Javatro Rules" + " 🃏 ♦️ ♣️" + UI.END;

        String[] lines = {
            "1. Start with a basic deck of cards.",
            "2. Each turn, draw cards and form poker hands to attack or defend.",
            "3. Earn new cards after each battle and upgrade your deck.",
            "4. If your health reaches zero, the run ends—try again with a new strategy!",
            "5. Different enemies and challenges appear in each run.",
            "",
            "Remember: Careful deck management is key to survival!"
        };

        UI.printBorderedContent(title, List.of(lines));
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}
