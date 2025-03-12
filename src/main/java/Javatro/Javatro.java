package Javatro;

import javatro_manager.JavatroManager;

import javatro_view.JavatroView;
import static Javatro.Card.Rank.ACE;
import static Javatro.Card.Rank.TEN;
import static Javatro.Card.Rank.THREE;
import static Javatro.Card.Suit.HEARTS;

import java.util.List;

/** The main class for the Javatro game. This class runs the game. */
import java.io.IOException;

// Primary view class that handles current view state etc
public class Javatro {
    private static final JavatroView javatroView = new JavatroView(); // View
    private static final JavatroManager javatroManager = new JavatroManager(javatroView);

    public static void main(String[] args) throws IOException {
        javatroManager.startGame();
        // Create a hand of cards
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS), // Using static imports for Rank and Suit
                        new Card(ACE, HEARTS), // Simplified card construction
                        new Card(THREE, HEARTS),
                        new Card(TEN, HEARTS),
                        new Card(TEN, HEARTS));

        // Calculate total chips from the cards in the hand
        int totalChips = 0;
        for (Card card : hand) {
            System.out.println(card + " - Chips: " + card.getChips());
            totalChips += card.getChips();
        }

        // Evaluate the poker hand and get the result
        PokerHand result = HandResult.evaluateHand(hand);
        System.out.println("Hand: " + result);

        // Add the base chips from the hand result to the total chips
        totalChips += result.getChips();

        // Calculate the total score by multiplying total chips with the multiplier
        int totalScore = totalChips * result.getMultiplier();

        // Display the results
        System.out.printf(
                "\nTotal Score Gained: %s Chips x %d Multiplier = %d\n",
                totalChips, result.getMultiplier(), totalScore);
        System.out.println("Current Round Score: 320");
        System.out.println("Blind Score to beat: 500");
    }
}
