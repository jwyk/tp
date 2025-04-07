// @@author swethacool
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
        String title = "Javatro Rules";

        String[] lines = {
            "1. Start with a basic deck of cards.                                                  "
                + "    ",
            "2. Each turn, draw cards and form poker hands.                                        "
                + "    ",
            "3. Use your best hands to score points and progress through rounds.                   "
                + "    ",
            "4. Earn new cards after each round and upgrade your deck.                             "
                + "    ",
            "5. The run ends when you fail to meet the score threshold—try again with a new"
                + " strategy!  ",
            "6. Each run introduces new challenges to test your poker skills.                      "
                + "    ",
            "",
            "Remember: Strategic hand selection and deck management are key to success!            "
                + "    "
        };

        UI.printBorderedContent(title, List.of(lines));
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}
