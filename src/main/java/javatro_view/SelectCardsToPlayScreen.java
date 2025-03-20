package javatro_view;

import javatro_manager.MakeSelectionCommand;

/**
 * The {@code SelectCardsToPlayScreen} class represents a screen where the user selects cards to
 * play. It extends {@code SelectionScreen} and allows selecting up to a predefined limit of cards.
 */
public class SelectCardsToPlayScreen extends SelectionScreen {

    /**
     * Constructs a {@code SelectCardsToPlayScreen} and sets the selection limit. Allows selecting
     * up to 5 cards to play and initializes the selection command.
     */
    public SelectCardsToPlayScreen() {
        super.SELECTION_LIMIT = 5; // Maximum of 5 cards can be selected to play
        super.commandMap.add(new MakeSelectionCommand(SELECTION_LIMIT));
    }

    /** Displays the current cards in the user's holding hand for selection. */
    @Override
    public void displayScreen() {
        super.displayCardsInHoldingHand();
    }
}
