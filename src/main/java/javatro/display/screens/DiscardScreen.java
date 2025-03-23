package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.SelectCardsOption;

/**
 * The {@code DiscardScreen} class represents a screen where the user selects cards to discard. It
 * extends {@code PlayDiscardScreen} and includes commands for making a selection.
 */
public class DiscardScreen extends PlayDiscardScreen {

    /** Constructs a {@code DiscardScreen} and initializes the selection command. */
    public DiscardScreen() throws JavatroException {
        super();
        super.SELECTION_LIMIT = 5; // Maximum of 5 cards can be selected to discard
        super.commandMap.add(new SelectCardsOption(SELECTION_LIMIT));
    }

    /** Displays the current cards in the user's holding hand for selection. */
    @Override
    public void displayScreen() {
        super.displayCardsInHoldingHand();
    }
}
