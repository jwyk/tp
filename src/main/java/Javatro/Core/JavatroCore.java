/**
 * The {@code JavatroCore} class represents the main game model, responsible for managing game
 * rounds and initializing the game state.
 */
package Javatro.Core;

import Javatro.Exception.JavatroException;

/** The core game logic class that manages the game state and rounds. */
public class JavatroCore {

    /** The current active round in the game. */
    public static Round currentRound;

    /**
     * Starts a new round and assigns it to the current round.
     *
     * @param round The new round to start.
     * @throws JavatroException If an error occurs while initializing the round.
     */
    private void startNewRound(Round round) throws JavatroException {
        currentRound = round;
    }

    /**
     * Creates a new classic round with predefined settings.
     *
     * @return A {@code Round} instance configured as a classic round.
     * @throws JavatroException If an error occurs while creating the round.
     */
    private Round classicRound() throws JavatroException {
        Deck d = new Deck();
        return new Round(1, 1, d, "Classic", "Classic Round");
    }

    /**
     * Starts the game by initializing a new round. This method is called when the game begins.
     *
     * @throws JavatroException If an error occurs while starting the game.
     */
    public void beginGame() throws JavatroException {
        startNewRound(classicRound());
    }
}
