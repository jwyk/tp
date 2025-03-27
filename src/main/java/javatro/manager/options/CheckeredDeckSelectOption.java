package javatro.manager.options;

import javatro.core.Deck;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;

public class CheckeredDeckSelectOption implements Option {

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    public String getDescription() {
        return "Checkered Deck: Start with 26 Hearts ♥\uFE0F, 26 Spades ♠\uFE0F";
    }

    /**
     * Executes the command to start with a Red Deck.
     *
     * @throws JavatroException if an error occurs during card selection.
     */
    @Override
    public void execute() throws JavatroException {
        Deck.DeckType deckType = Deck.DeckType.CHECKERED;
        JavatroManager.beginGame(deckType);
        javatro.display.UI.getGameScreen().restoreGameCommands();
        // Update the main screen to show the game screen
        JavatroManager.setScreen(UI.getGameScreen());


    }
}
