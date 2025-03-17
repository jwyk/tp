package javatro_view;

import javatro_manager.MakeSelectionCommand;

public class SelectCardsToDiscardScreen extends SelectionScreen {

    public SelectCardsToDiscardScreen() {
        super.commandMap.add(new MakeSelectionCommand(-1));
    }

    @Override
    public void displayScreen() {
        super.displayCardsInHoldingHand();
    }
}
