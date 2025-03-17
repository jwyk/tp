/**
 * The {@code MakeSelectionCommand} class represents a command that allows the player to make a
 * selection of cards to either play or discard, based on the current game screen.
 */
package javatro_manager;

import javatro_core.JavatroCore;

import javatro_exception.JavatroException;

import javatro_view.JavatroView;
import javatro_view.SelectCardsToDiscardScreen;
import javatro_view.SelectCardsToPlayScreen;

import java.util.List;

/** A command that enables players to make a selection of cards to play or discard. */
public class MakeSelectionCommand implements Command {

    /** The maximum number of cards that can be selected. */
    private final int LIMIT;

    /**
     * Constructs a {@code MakeSelectionCommand} with a specified selection limit.
     *
     * @param LIMIT The maximum number of cards that can be selected (-1 for unlimited selection).
     */
    public MakeSelectionCommand(int LIMIT) {
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
                    JavatroView.getCardInput(
                            JavatroCore.currentRound.getPlayerHand().size(),
                            JavatroCore.currentRound.getPlayerHand().size());
        } else {
            userInput =
                    JavatroView.getCardInput(
                            JavatroCore.currentRound.getPlayerHand().size(), LIMIT);
        }

        if (JavatroView.getCurrentScreen() instanceof SelectCardsToPlayScreen) {
            // Select and play the chosen cards
            JavatroCore.currentRound.playCards(userInput);
        } else if (JavatroView.getCurrentScreen() instanceof SelectCardsToDiscardScreen) {
            // Discard the selected cards
            JavatroCore.currentRound.discardCards(userInput);
        }

        // Return to the game screen after selection
        JavatroManager.setScreen(JavatroView.getGameScreen());
    }
}
