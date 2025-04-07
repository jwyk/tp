// @@author swethacool
package javatro.manager.options;

import static javatro.display.UI.BOLD;
import static javatro.display.UI.END;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;

/**
 * The HelpTipsOption class provides gameplay tips for javatro. This command is executed when the
 * player requests tips for improving their strategy.
 */
public class HelpTipsOption implements Option {

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Tips and Tricks";
    }

    /** Executes the command to display gameplay tips. */
    @Override
    public void execute() throws JavatroException {
        String title =                            "Pro Tips For Javatro";

        String[] lines = {
                " - Focus on building a well-rounded deck with versatile poker hands.                        ",
                " - The blind level you choose influences your gameplay.                                     ",
                " - Choose the deck that suits your play style as each deck provides unique advantages.      ",
                " - Avoid overloading your deck—some cards can reduce your chances of strong combinations.   ",
                " - Don't rush through rounds. Think carefully about which cards to play or discard.         ",
                " - Save powerful hands for high-stakes rounds or tougher challenges.                        ",
                " - Adjust your strategy based on the poker hands you draw and your current blind.           ",
                " - Try different combinations and playstyles to discover what works best for your deck.     ",
                " - Utilize Special Cards like Joker and Planet Cards wisely for extra benefits.             ",
                "                                                                                            ",
                "                                  Good luck and have fun!                                   "
        };

        UI.printBorderedContent(title, List.of(lines));
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}
