package javatro_view;

import javatro_manager.MakeSelectionCommand;

public class SelectCardsToPlayScreen extends SelectionScreen {

    public SelectCardsToPlayScreen() {
        super.SELECTION_LIMIT = 5; // You can select a maximum of 5 cards to play
        super.commandMap.add(new MakeSelectionCommand(SELECTION_LIMIT));
    }

    @Override
    public void displayScreen() {
        super.displayCardsInHoldingHand();
    }
}
