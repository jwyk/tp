package javatro_core;

import javatro_exception.JavatroException;

// Main Model class
public class JavatroCore {
    public static Round currentRound; // Instance of round class

    // Start a round and assign to current round
    private void startNewRound(
            Round round)
            throws JavatroException {
        currentRound = round;
    }

    private Round classicRound() throws JavatroException {
        Deck d = new Deck();
        return new Round(1200, 10, d, "Classic", "Classic Round");
    }

    // Called when start game is selected
    public void beginGame() throws JavatroException {
        startNewRound(classicRound());
    }

}
