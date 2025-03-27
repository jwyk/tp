package javatro.manager.options;

import javatro.core.Deck.DeckType;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class AbandonedDeckSelectOption implements Option {

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    public String getDescription() {
        return "Abandoned Deck: Start with no Face Cards (K, Q, J)";
    }

    /**
     * Executes the command to start with a Blue Deck.
     *
     * @throws JavatroException if an error occurs during card selection.
     */
    @Override
    public void execute() throws JavatroException {
        DeckType deckType = DeckType.ABANDONED;
        JavatroManager.beginGame(deckType);
        javatro.display.UI.getGameScreen().restoreGameCommands();
        // Update the main screen to show the game screen
        JavatroManager.setScreen(UI.getGameScreen());
    }
}
