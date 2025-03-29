package javatro.manager.options;

import javatro.core.Deck;
import javatro.core.Deck.DeckType;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class BlueDeckSelectOption implements Option {

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    public String getDescription() {
        return "Blue Deck: +1 Hands per Round";
    }

    /**
     * Executes the command to start with a Blue Deck.
     *
     * @throws JavatroException if an error occurs during card selection.
     */
    @Override
    public void execute() throws JavatroException {
        DeckType deckType = DeckType.BLUE;
        JavatroManager.jc.deck = new Deck(deckType);
        JavatroManager.jc.setupNewGame(deckType);
        JavatroManager.setScreen(UI.getBlindScreen());
//        JavatroManager.beginGame(deckType);
//        javatro.display.UI.getGameScreen().restoreGameCommands();
//        // Update the main screen to show the game screen
//        JavatroManager.setScreen(UI.getGameScreen());
    }
}
