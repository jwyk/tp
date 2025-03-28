/**
 * The {@code JavatroCore} class represents the main game model, responsible for managing game
 * rounds and initializing the game state.
 */
package javatro.core;

import javatro.core.jokers.HeldJokers;
import javatro.manager.JavatroManager;

/** The core game logic class that manages the game state and rounds. */
public class JavatroCore {

    /** The current active round in the game. */
    public static Round currentRound;

    /** The number of plays give per round (Default value = 4). */
    public static int totalPlays;

    /** The deck used throughout the game. (A copy of this deck is made for every new Round) */
    public static Deck deck;

    /** The deck used throughout the game. (A copy of this deck is made for every new Round) */
    public static HeldJokers heldJokers;

    /**
     * Starts a new round and assigns it to the current round.
     *
     * @param round The new round to start.
     * @throws JavatroException If an error occurs while initializing the round.
     */
    private void startNewRound(Round round) {
        currentRound = round;
    }
    /**
     * Creates a new classic round with predefined settings.
     *
     * @return A {@code Round} instance configured as a classic round.
     * @throws JavatroException If an error occurs while creating the round.
     */
    private Round classicRound() {
        Deck d;
        try {
            d = new Deck(deck);
            d.shuffle();
            return new Round(300, 4, d, "Classic", "Classic Round");
        } catch (JavatroException javatroException) {
            System.out.println(javatroException.getMessage());
        }
        return null;
    }

    /**
     * Starts the game by initializing a new round. This method is called when the game begins.
     *
     * @throws JavatroException If an error occurs while starting the game.
     */
    //    public void beginGame(Deck.DeckType deckType) {
    public void beginGame() throws JavatroException {
        totalPlays = 4;
        //        deck = new Deck(deckType);
        JavatroManager.setScreen(javatro.display.UI.getBlindScreen());
        startNewRound(classicRound());
    }
}
