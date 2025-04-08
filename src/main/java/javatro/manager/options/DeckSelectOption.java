// @@author Markneoneo
package javatro.manager.options;

import javatro.core.Deck;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;
import javatro.storage.DataParser;
import javatro.storage.Storage;
import javatro.storage.utils.CardUtils;

/**
 * Represents a menu option for selecting a specific deck type to start a new game. When executed,
 * initializes the game with the specified deck and transitions to the blind screen.
 */
public class DeckSelectOption implements Option {

    /** The descriptive text displayed for this option in the menu. */
    private final String description;

    /** The type of deck associated with this option. */
    private final Deck.DeckType deckType;

    /**
     * Constructs a deck selection option with specified description and deck type.
     *
     * @param description User-visible description of the option
     * @param deckType The type of deck to initialize when selected
     * @throws NullPointerException if description or deckType are null
     */
    public DeckSelectOption(String description, Deck.DeckType deckType) {
        assert description != null : "Description cannot be null";
        assert deckType != null : "DeckType cannot be null";

        this.description = description;
        this.deckType = deckType;
    }

    /**
     * {@inheritDoc}
     *
     * @return The description text for this deck selection option
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc} Initializes game with selected deck type and transitions to blind screen.
     *
     * @throws JavatroException if game initialization fails
     */
    @Override
    public void execute() throws JavatroException {
        // Initialize core game deck
        Storage storage = Storage.getStorageInstance();
        JavatroCore.deck = new Deck(deckType);
        storage.setValue(storage.getRunChosen() - 1, DataParser.DECK_INDEX, deckType.getName());
        JavatroManager.beginGame(
                (CardUtils.DeckFromKey(
                        storage.getValue(storage.getRunChosen() - 1, DataParser.DECK_INDEX))));
        assert JavatroCore.deck != null : "Deck initialization failed";

        // Start game session with selected deck
        // JavatroManager.beginGame(deckType);

        // Transition to blind screen display
        JavatroManager.setScreen(UI.getBlindScreen());
    }
}
