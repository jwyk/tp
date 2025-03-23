package javatro.display.screens;

import javatro.core.Card;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.manager.options.CardSelectOption;
import javatro.manager.options.ResumeGameOption;

import java.util.List;

/**
 * The {@code CardSelectScreen} class represents an abstract screen where users select cards from
 * their hand. It provides methods for updating and displaying the player's current hand. This class
 * is intended to be extended by specific screens like {@code DiscardScreen} and {@code PlayScreen}.
 *
 * <p>The class includes functionality to:
 *
 * <ul>
 *   <li>Update the player's current hand of cards.
 *   <li>Display the player's hand in a formatted layout.
 *   <li>Provide a default selection limit for card selection.
 * </ul>
 *
 * @see Screen
 */
public abstract class CardSelectScreen extends Screen {

    /** The list of cards currently in the player's hand. */
    private List<Card> holdingHand;

    /**
     * Constructs a {@code CardSelectScreen} with a custom options title and initializes it with a
     * resume game command.
     *
     * @param optionsTitle The title to display for the option menu.
     * @throws JavatroException if an error occurs during initialization.
     * @throws IllegalArgumentException if the options title is null or empty.
     */
    public CardSelectScreen(String optionsTitle) throws JavatroException {
        super(optionsTitle);

        // Add the "Select Cards" and "Resume Game" options
        super.commandMap.add(new CardSelectOption());
        super.commandMap.add(new ResumeGameOption());
    }

    /** Updates the holding hand by retrieving the player's current hand from the game core. */
    public void updateHoldingHand() {
        this.holdingHand = JavatroCore.currentRound.getPlayerHand();
    }

    /**
     * Displays the player's current hand of cards in a formatted layout. The cards are displayed
     * with their indices and simplified representations.
     */
    protected void displayHoldingHand() {
        updateHoldingHand();
        if (holdingHand.isEmpty()) {
            System.out.println("YOUR CARDS: No cards in hand.");
            return;
        }

        System.out.println("YOUR CARDS:");

        // Display card indices
        StringBuilder numberHeaders = new StringBuilder();
        for (int i = 0; i < holdingHand.size(); i++) {
            numberHeaders
                    .append("|")
                    .append(GameScreen.getDisplayStringCenter(Integer.toString(i + 1), 5))
                    .append("|    ");
        }
        System.out.println(numberHeaders.toString());

        // Display card headers
        String cardHeaders = (GameScreen.getHeaderString(5) + "    ").repeat(holdingHand.size());
        System.out.println(cardHeaders);

        // Display card values
        StringBuilder cardValues = new StringBuilder();
        for (Card card : holdingHand) {
            cardValues
                    .append("|")
                    .append(GameScreen.getDisplayStringCenter(card.getSimplified(), 5))
                    .append("|    ");
        }
        System.out.println(cardValues.toString());
        System.out.println(cardHeaders);
    }

    /**
     * Displays the screen. This method is intended to be overridden by subclasses to provide
     * specific screen display behavior.
     */
    @Override
    public abstract void displayScreen();
}
