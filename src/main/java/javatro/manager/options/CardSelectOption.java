package javatro.manager.options;

import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.DiscardScreen;
import javatro.display.screens.PlayScreen;
import javatro.manager.JavatroManager;
import javatro.display.Parser;

import java.util.List;

/**
 * The {@code CardSelectOption} class represents a command that allows the player to make a
 * selection of cards to either play or discard, based on the current game screen.
 */
public class CardSelectOption implements Option {

    /** The default selection limit for cards. */
    public static final int DEFAULT_SELECTION_LIMIT = 5;

    /** The maximum number of cards that can be selected. */
    private final int selectionLimit;

    /**
     * Constructs a {@code CardSelectOption} with the default selection limit of 5.
     */
    public CardSelectOption() throws JavatroException {
        this(DEFAULT_SELECTION_LIMIT);
    }

    /**
     * Constructs a {@code CardSelectOption} with a specified selection limit.
     *
     * @param selectionLimit The maximum number of cards that can be selected.
     * @throws JavatroException if the selection limit is invalid (less than 1).
     */
    public CardSelectOption(int selectionLimit) throws JavatroException {
        if (selectionLimit < 1) {
            throw JavatroException.invalidSelectionLimit();
        }
        this.selectionLimit = selectionLimit;
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Select Cards";
    }

    /**
     * Executes the selection command, prompting the player to select cards to either play or
     * discard. The behavior changes based on the current screen.
     *
     * @throws JavatroException if an error occurs during card selection.
     */
    @Override
    public void execute() throws JavatroException {
        List<Integer> userInput = Parser.getCardInput(JavatroCore.currentRound.getPlayerHand().size(), selectionLimit);

        if (UI.getCurrentScreen() instanceof PlayScreen) {
            // Select and play the chosen cards
            JavatroCore.currentRound.playCards(userInput);
        } else if (UI.getCurrentScreen() instanceof DiscardScreen) {
            // Discard the selected cards
            JavatroCore.currentRound.discardCards(userInput);
        }

        // Return to the game screen after selection
        JavatroManager.setScreen(UI.getGameScreen());
    }
}