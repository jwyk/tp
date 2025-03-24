package javatro.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the {@link Deck} class. It tests the initialisation and
 * drawing of cards, ensuring the deck contains the proper number of cards.
 */
public class DeckTest {

    private static Deck deck;

    /** Initialize a new deck for each test. */
    @BeforeEach
    void init() {
        deck = new Deck();
    }

    /**
     * Test that each Deck initialised has 52 cards, and drawing 1 card gives the correct number of
     * cards remaining.
     */
    @Test
    void testDeck() throws JavatroException {
        int cardsRemaining = deck.getRemainingCards();
        assertEquals(52, cardsRemaining);
        Card drawOne = deck.draw();
        assertEquals(51, deck.getRemainingCards());
    }
}
