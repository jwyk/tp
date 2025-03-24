package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.screens.CardSelectScreen;
import javatro.manager.JavatroManager;

/**
 * An option that sorts the current holding hand by rank (Ace > King > Queen > ... > Two) and
 * redisplay the hand.
 */
public class SortByRankOption implements Option {
    private final CardSelectScreen screen;

    public SortByRankOption(CardSelectScreen screen) {
        this.screen = screen;
    }

    @Override
    public String getDescription() {
        return "Sort cards by Rank";
    }

    @Override
    public void execute() throws JavatroException {
        // Update the screen's holding hand with sorted order
        screen.updateHoldingHand(CardSelectScreen.SortOrder.BY_RANK);
        // Refresh the entire screen
        JavatroManager.setScreen(screen);
    }
}
