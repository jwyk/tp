// @@author swethacool
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;

/**
 * The HelpHowOption class explains how to play javatro. This command is executed when the player
 * requests gameplay instructions.
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
        String title =                     "How To Play Javatro" ;

        String[] lines = {
                "                          Welcome to the Javatro!                          ",
                "                   Here's how to play and navigate the game:                ",
                " 1. Start a new game from the Main Menu by selecting [1]                    ",
                " 2. Select the deck you desire from [1] to [4]                              ",
                " 3. Choose your blind level by either accepting [1] or rejecting [2]        ",
                " 4. Play cards [1] and form valid poker hands                               ",
                " 5. Discard cards [2] you don't want to strategize your deck                ",
                " 6. View deck [4] or poker hands [3] anytime                                ",
                " 7. Follow menu navigation by entering the number for desired commands      ",
                " 8. Build your strategy and aim for the highest score!                      ",
                "                                                                            ",
                "                         Ante and Blind System:                             ",
                " - Each round starts with an ante and blind selection                       ",
                " - The ante increases as you progress through rounds                        ",
                " - Blinds rotate: Small Blind, Large Blind, and Boss Blind                  ",
                " - Score requirements increase progressively                                ",
                " - Only Boss Blind is compulsory; others are optional                       ",
                " - Higher blinds mean higher risks and rewards!                             ",
                "                                                                            ",
                "              Have fun playing and may the best hand win!                   "
        };

        UI.printBorderedContent(title, List.of(lines));
        JavatroManager.setScreen(UI.getHelpScreen());
    }
}
