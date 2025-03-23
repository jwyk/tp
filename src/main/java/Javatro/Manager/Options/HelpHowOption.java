package Javatro.Manager.Options;

import Javatro.Core.JavatroException;
import Javatro.Manager.JavatroManager;
import Javatro.Display.UI;

/**
 * The HelpHowOption class explains how to play Javatro. This command is executed when the
 * player requests gameplay instructions.
 */
public class HelpHowOption implements Option {

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "How To Play";
    }

    /** Executes the command to display instructions on how to play. */
    @Override
    public void execute() throws JavatroException {
        String title = "♥️ ♠️ 🃏 " + UI.BOLD + "How To Play Javatro" + UI.END + " 🃏 ♦️ ♣️";

        String[] lines = {
                "1. Start a new game with 'start'.",
                "2. Draw cards using 'draw'.",
                "3. Play poker hands to attack enemies.",
                "4. Earn new cards and modify your deck.",
                "5. Survive as long as possible and improve your strategy.",
                "",
                "Use commands like:",
                "   - 'attack'  : Play a hand to deal damage.",
                "   - 'defend'  : Use a card to reduce incoming damage.",
                "   - 'exit'    : Quit the game."
        };

        UI.printBorderedMessage(title, lines);
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}