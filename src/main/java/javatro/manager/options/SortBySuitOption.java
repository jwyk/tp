package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.screens.CardSelectScreen;
import javatro.manager.JavatroManager;

/**
 * An option that sorts the current holding hand by suit (Spades > Hearts > Clubs > Diamonds) and
 * redisplay the hand.
 */
public class SortBySuitOption implements Option {
    private final CardSelectScreen screen;

    public SortBySuitOption(CardSelectScreen screen) {
        this.screen = screen;
    }

    @Override
    public String getDescription() {
        return "Sort cards by Suit";
    }

    @Override
    public void execute() throws JavatroException {
        // Update the screen's holding hand with sorted order
        screen.updateHoldingHand(CardSelectScreen.SortOrder.BY_SUIT);
        // Refresh the entire screen
        JavatroManager.setScreen(screen);
    }
}
