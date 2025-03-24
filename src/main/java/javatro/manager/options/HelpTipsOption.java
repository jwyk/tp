package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

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
        String title = "♥️ ♠️ 🃏 " + UI.BOLD + "Pro Tips For Javatro" + UI.END + " 🃏 ♦️ ♣️";

        String[] lines = {
            "- Focus on building a balanced deck with attack and defense cards.",
            "- Don't just add every card you find—some cards can weaken your deck.",
            "- Save strong hands for tougher enemies.",
            "- Adapt your strategy based on the enemies you encounter.",
            "- Experiment with different playstyles to find what works best for you.",
            "",
            "Good luck and have fun!"
        };

        UI.printBorderedMessage(title, lines);
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}
