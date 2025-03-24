package javatro.display.screens;

import javatro.core.JavatroException;

/**
 * The {@code PlayScreen} class represents a screen where the user selects cards to play. It extends
 * {@code CardSelectScreen} and allows selecting up to a predefined limit of cards (default is 5).
 *
 * @see CardSelectScreen
 */
public class PlayScreen extends CardSelectScreen {

    /**
     * Constructs a {@code PlayScreen} and sets the selection limit. The user can select up to 5
     * cards to play, and the selection command is initialized.
     *
     * @throws JavatroException if an error occurs during initialization.
     */
    public PlayScreen() throws JavatroException {
        super("SELECT CARDS TO PLAY");
    }

    /**
     * Displays the current cards in the user's holding hand for selection. This method overrides
     * the parent class implementation to provide specific behavior for playing cards.
     */
    @Override
    public void displayScreen() {
        super.displayHoldingHand();
    }
}
