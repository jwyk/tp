// @@author Markneoneo
package javatro.manager.options;

import javatro.core.Ante;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.core.PokerHand;
import javatro.display.Parser;
import javatro.display.UI;
import javatro.display.screens.DiscardCardScreen;
import javatro.display.screens.PlayCardScreen;
import javatro.manager.JavatroManager;

import java.util.List;

import static javatro.display.UI.BLACK_B;
import static javatro.display.UI.BOLD;
import static javatro.display.UI.END;
import static javatro.display.UI.YELLOW;
import static javatro.display.UI.getCardArtLines;
import static javatro.display.UI.printBorderedContent;

/**
 * The {@code CardSelectOption} class handles card selection operations for the player, allowing them
 * to either play cards into their current poker hand or discard cards, depending on the active
 * game screen. The number of selectable cards is limited by a configurable maximum (default: 5).
 */
public class CardSelectOption implements Option {

    /** Default maximum number of cards that can be selected in one operation */
    public static final int DEFAULT_SELECTION_LIMIT = 5;

    /** Maximum allowed number of cards to select in this operation */
    private final int selectionLimit;

    /**
     * Constructs a card selector with default selection limit ({@value DEFAULT_SELECTION_LIMIT}).
     *
     * @throws JavatroException if initialization fails due to internal constraints
     */
    public CardSelectOption() throws JavatroException {
        this(DEFAULT_SELECTION_LIMIT);
    }

    /**
     * Constructs a card selector with a custom selection limit.
     *
     * @param selectionLimit Maximum number of cards that can be selected (must be ≥ 1)
     * @throws JavatroException if invalid selection limit is provided
     */
    public CardSelectOption(int selectionLimit) throws JavatroException {
        if (selectionLimit < 1) {
            throw JavatroException.invalidSelectionLimit();
        }
        this.selectionLimit = selectionLimit;

        // Assert internal invariant after validation
        assert this.selectionLimit >= 1 : "Selection limit must be positive";
    }

    /**
     * Returns a human-readable description of this command.
     *
     * @return Brief string describing the card selection operation
     */
    @Override
    public String getDescription() {
        return "Select Cards";
    }

    /**
     * Executes the card selection operation. Collects user input, processes the selection based on
     * current context (play/discard), updates game state, and handles game progression.
     *
     * @throws JavatroException if input parsing fails or game state becomes inconsistent
     */
    @Override
    public void execute() throws JavatroException {
        // Get validated card selection from user
        List<Integer> userInput = Parser.getCardInput(
                JavatroCore.currentRound.getPlayerHandCards().size(),
                selectionLimit
        );

        // Validate input assumptions
        assert userInput != null : "Card input must not be null";
        assert userInput.size() <= selectionLimit :
                "Selected card count exceeds limit: " + userInput.size();

        // Process selection based on current screen context
        if (UI.getCurrentScreen() instanceof PlayCardScreen) {
            // Play selected cards as part of poker hand
            JavatroCore.currentRound.playCards(userInput);

            // Display played hand information
            PokerHand playedHand = JavatroCore.currentRound.getPlayedHand();
            String handName = String.format(
                    "Achieved: %s%s<%s>%s%s Hand",
                    BOLD, YELLOW, playedHand.getHandName(), END, BLACK_B
            );
            List<String> cardArtLines = getCardArtLines(JavatroCore.currentRound.getPlayedCards());
            printBorderedContent(handName, cardArtLines);

            // Update statistics for played hand type
            playedHand.incrementPlayed();

        } else if (UI.getCurrentScreen() instanceof DiscardCardScreen) {
            // Remove selected cards from play
            JavatroCore.currentRound.discardCards(userInput);

            // Display discard confirmation
            String handName = String.format("%sCards Discarded%s%s", BOLD, END, BLACK_B);
            List<String> cardArtLines = getCardArtLines(JavatroCore.currentRound.getPlayedCards());
            printBorderedContent(handName, cardArtLines);
        }

        // Handle game progression based on round outcome
        if (JavatroCore.currentRound.isWon()
                && JavatroCore.getAnte().getAnteCount() == 8
                && JavatroCore.getAnte().getBlind() == Ante.Blind.BOSS_BLIND) {
            // Final victory condition: Defeated boss blind at maximum ante
            JavatroManager.setScreen(UI.getWinGameScreen());
        } else if (JavatroCore.currentRound.isLost()) {
            // Game over condition: Failed current round
            JavatroManager.setScreen(UI.getLoseScreen());
        } else if (JavatroCore.currentRound.isWon()) {
            // Round victory: Advance to next round
            JavatroManager.setScreen(UI.getWinRoundScreen());
        } else {
            // Continue current round
            JavatroManager.setScreen(UI.getGameScreen());
        }
    }
}