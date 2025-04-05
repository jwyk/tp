// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.screens.CardSelectScreen;
import javatro.manager.JavatroManager;

/**
 * Organizes card display using suit-based sorting (Spades, Hearts, Clubs, Diamonds). Updates both
 * the data model and display when executed.
 */
public class SortBySuitOption implements Option {

    /** The card selection screen to modify. */
    private final CardSelectScreen screen;

    /**
     * Creates a suit sorting option for specified card screen.
     *
     * @param screen Card display interface to modify
     * @throws NullPointerException if screen is null
     */
    public SortBySuitOption(CardSelectScreen screen) {
        assert screen != null : "Card screen must be initialized";
        this.screen = screen;
    }

    /**
     * {@inheritDoc}
     *
     * @return Constant description "Sort cards by Suit"
     */
    @Override
    public String getDescription() {
        return "Sort cards by Suit";
    }

    /**
     * {@inheritDoc} Applies suit-based sorting and refreshes display.
     *
     * @throws JavatroException if display update fails
     */
    @Override
    public void execute() throws JavatroException {
        // Apply suit-based sorting to card collection
        screen.updateHoldingHand(CardSelectScreen.SortOrder.BY_SUIT);

        // Refresh display with new card order
        JavatroManager.setScreen(screen);
    }
}
