package Javatro.Manager.Options;

import Javatro.Core.JavatroException;
import Javatro.Manager.JavatroManager;
import Javatro.Display.UI;

/**
 * The HelpIntroOption class provides an introduction to Javatro. This command is executed when the
 * player requests an introduction.
 */
public class HelpIntroOption implements Option {

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Game Introduction";
    }

    /** Executes the command to display the introduction message. */
    @Override
    public void execute() throws JavatroException {
        String title = "♥️ ♠️ 🃏 " + UI.BOLD + "Welcome to Javatro" + UI.END + " 🃏 ♦️ ♣️";

        String[] lines = {
                "Javatro is a text-based roguelike deck-building game inspired by Balatro.",
                "Your goal is to strategically build a deck and progress through challenges.",
                "Each turn, you'll play poker-style hands to defeat enemies and earn new cards.",
                "",
                "Think strategically, manage your deck wisely, and see how far you can go!"
        };

        UI.printBorderedMessage(title, lines);
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}