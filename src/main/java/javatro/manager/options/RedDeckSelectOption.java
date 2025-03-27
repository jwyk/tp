package javatro.manager.options;

import javatro.core.Deck.DeckType;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class RedDeckSelectOption implements Option {

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    public String getDescription() {
        return "Red Deck: +1 Discards per Round";
    }

    /**
     * Executes the command to start with a Red Deck.
     *
     * @throws JavatroException if an error occurs during card selection.
     */
    @Override
    public void execute() throws JavatroException {
        DeckType deckType = DeckType.RED;
        JavatroManager.beginGame(deckType);
        javatro.display.UI.getGameScreen().restoreGameCommands();
        // Update the main screen to show the game screen
        JavatroManager.setScreen(UI.getGameScreen());
    }
}
