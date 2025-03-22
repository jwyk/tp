/**
 * The {@code MakeSelectionOption} class represents a command that allows the player to make a
 * selection of cards to either play or discard, based on the current game screen.
 */
package Javatro.Manager.Options;

import Javatro.Core.JavatroCore;
import Javatro.Exception.JavatroException;
import Javatro.Manager.JavatroManager;
import Javatro.UI.Screens.SelectCardsToDiscardScreen;
import Javatro.UI.Screens.SelectCardsToPlayScreen;
import Javatro.UI.UI;

import java.util.List;

/** A command that enables players to make a selection of cards to play or discard. */
public class MakeSelectionOption implements Option {

    /** The maximum number of cards that can be selected. */
    private final int LIMIT;

    /**
     * Constructs a {@code MakeSelectionOption} with a specified selection limit.
     *
     * @param LIMIT The maximum number of cards that can be selected (-1 for unlimited selection).
     */
    public MakeSelectionOption(int LIMIT) {
        this.LIMIT = LIMIT;
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Make Selection";
    }

    /**
     * Executes the selection command, prompting the player to select cards to either play or
     * discard. The behavior changes based on the current screen.
     *
     * @throws JavatroException If an error occurs during card selection.
     */
    @Override
    public void execute() throws JavatroException {
        List<Integer> userInput;

        if (LIMIT == -1) {
            userInput =
                    Javatro.UI.UI.getCardInput(
                            JavatroCore.currentRound.getPlayerHand().size(),
                            JavatroCore.currentRound.getPlayerHand().size());
        } else {
            userInput =
                    Javatro.UI.UI.getCardInput(
                            JavatroCore.currentRound.getPlayerHand().size(), LIMIT);
        }

        if (Javatro.UI.UI.getCurrentScreen() instanceof SelectCardsToPlayScreen) {
            // Select and play the chosen cards
            JavatroCore.currentRound.playCards(userInput);
        } else if (UI.getCurrentScreen() instanceof SelectCardsToDiscardScreen) {
            // Discard the selected cards
            JavatroCore.currentRound.discardCards(userInput);
        }

        // Return to the game screen after selection
        JavatroManager.setScreen(Javatro.UI.UI.getGameScreen());
    }
}
