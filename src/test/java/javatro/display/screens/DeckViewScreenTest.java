package javatro.display.screens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javatro.core.Card;
import javatro.core.Deck;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DeckViewScreenTest {

    private DeckViewScreen deckViewScreen;
    private Deck testDeck;

    @BeforeEach
    public void setUp() throws JavatroException {
        deckViewScreen = new DeckViewScreen();

        // Create a test deck with a mix of cards
        testDeck = new Deck(Deck.DeckType.DEFAULT);
        ArrayList<Card> cards = new ArrayList<>();

        JavatroCore.deck = testDeck;
    }

    @Test
    public void testBuildCountMatrixCountsCorrectly() {
        List<Card> cards = testDeck.getWholeDeck();
        DeckViewScreen.DeckCountData countData =
                deckViewScreen.buildCountMatrix(new ArrayList<>(cards));

        assertEquals(13, countData.suitTotals[0]); // Spades
        assertEquals(13, countData.suitTotals[1]); // Hearts
        assertEquals(13, countData.suitTotals[2]); // Clubs
        assertEquals(13, countData.suitTotals[3]); // Diamonds
    }

    @Test
    public void testBuildDeckTableFormatting() {
        List<Card> cards = testDeck.getWholeDeck();
        DeckViewScreen.DeckCountData countData =
                deckViewScreen.buildCountMatrix(new ArrayList<>(cards));

        StringBuilder table = deckViewScreen.buildDeckTable("Test Deck", countData);

        assertNotNull(table, "Generated table should not be null.");
        assertTrue(!table.isEmpty(), "Generated table should not be empty.");
    }

    @Test
    public void testGetSuitIndex() {
        assertEquals(0, deckViewScreen.getSuitIndex(Card.Suit.SPADES));
        assertEquals(1, deckViewScreen.getSuitIndex(Card.Suit.HEARTS));
        assertEquals(2, deckViewScreen.getSuitIndex(Card.Suit.CLUBS));
        assertEquals(3, deckViewScreen.getSuitIndex(Card.Suit.DIAMONDS));
    }

    @Test
    public void testGetRankIndex() {
        assertEquals(0, deckViewScreen.getRankIndex(Card.Rank.ACE));
        assertEquals(1, deckViewScreen.getRankIndex(Card.Rank.KING));
        assertEquals(2, deckViewScreen.getRankIndex(Card.Rank.QUEEN));
        assertEquals(3, deckViewScreen.getRankIndex(Card.Rank.JACK));
        assertEquals(4, deckViewScreen.getRankIndex(Card.Rank.TEN));
        assertEquals(5, deckViewScreen.getRankIndex(Card.Rank.NINE));
        assertEquals(12, deckViewScreen.getRankIndex(Card.Rank.TWO));
    }

    @Test
    public void testDisplayScreenWithNullDeck() {
        JavatroCore.deck = null;
        assertThrows(
                NullPointerException.class,
                () -> deckViewScreen.displayScreen(),
                "DeckViewScreen should throw an error when the deck is null.");
    }
}
