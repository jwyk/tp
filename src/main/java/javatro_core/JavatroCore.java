package javatro_core;

import Javatro.Deck;
import Javatro.JavatroException;
import Javatro.Round;

// Main Model class
public class JavatroCore {
    public static Round currentRound; // Instance of round class

    // Start a round and assign to current round
    private void startNewRound(
            int blindScore,
            int remainingPlays,
            Deck deck,
            String roundName,
            String roundDescription)
            throws JavatroException {
        currentRound = new Round(blindScore, remainingPlays, deck, roundName, roundDescription);
    }

    // Called when start game is selected
    public void beginGame() throws JavatroException {
        Deck d = new Deck();
        startNewRound(1200, 10, d, "The Eye", "No repeat hands");
    }
}
