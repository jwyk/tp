// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.screens.CardSelectScreen;
import javatro.manager.JavatroManager;

/**
 * Organizes card display using rank-based sorting (Ace high to Two low).
 * Updates both the data model and display when executed.
 */
public class SortByRankOption implements Option {

    /**
     * The card selection screen to modify.
     */
    private final CardSelectScreen screen;

    /**
     * Creates a rank sorting option for specified card screen.
     * @param screen Card display interface to modify
     * @throws NullPointerException if screen is null
     */
    public SortByRankOption(CardSelectScreen screen) {
        assert screen != null : "Card screen must be initialized";
        this.screen = screen;
    }

    /**
     * {@inheritDoc}
     * @return Constant description "Sort cards by Rank"
     */
    @Override
    public String getDescription() {
        return "Sort cards by Rank";
    }

    /**
     * {@inheritDoc}
     * Applies rank-based sorting and refreshes display.
     * @throws JavatroException if display update fails
     */
    @Override
    public void execute() throws JavatroException {
        // Apply rank-based sorting to card collection
        screen.updateHoldingHand(CardSelectScreen.SortOrder.BY_RANK);

        // Refresh display with new card order
        JavatroManager.setScreen(screen);
    }
}