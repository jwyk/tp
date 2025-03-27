/**
 * The {@code JavatroCore} class represents the main game model, responsible for managing game
 * rounds and initializing the game state.
 */
package javatro.core;

/** The core game logic class that manages the game state and rounds. */
public class JavatroCore {

    /** The current active round in the game. */
    public static Round currentRound;

    /** The number of plays give per round (Default value = 4). */
    public static int totalPlays;

    /** The deck used throughout the game. (A copy of this deck is made for every new Round) */
    public static Deck deck;

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
            d = deck.clone();
            d.shuffle();
            return new Round(300, 4, d, "Classic", "Classic Round");
        } catch (JavatroException javatroException) {
            System.out.println(javatroException.getMessage());
        } catch (CloneNotSupportedException e) {
            System.out.println(JavatroException.invalidDeck().getMessage());
        }
        return null;
    }

    /**
     * Starts the game by initializing a new round. This method is called when the game begins.
     *
     * @throws JavatroException If an error occurs while starting the game.
     */
    public void beginGame(Deck.DeckType deckType) {
        totalPlays = 4;
        deck = new Deck(deckType);

        //        //Override deck
        //        if (deckType.getName().equals("Red")) {
        //            totalPlays = 4;
        //        };

        startNewRound(classicRound());
    }
}
