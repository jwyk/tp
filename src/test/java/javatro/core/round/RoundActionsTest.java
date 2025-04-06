package javatro.core.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import javatro.core.BossType;
import javatro.core.Deck;
import javatro.core.HoldingHand;
import javatro.core.JavatroException;
import javatro.core.jokers.HeldJokers;
import javatro.display.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RoundActionsTest {

    private RoundState roundState;
    private RoundConfig roundConfig;
    private Deck deck;
    private HoldingHand playerHand;
    private HeldJokers playerJokers;

    @BeforeEach
    void setUp() throws JavatroException {
        // Initialize the deck with default cards
        deck = new Deck(Deck.DeckType.DEFAULT);

        // Create a player's hand and populate it with 8 cards from the deck
        playerHand = new HoldingHand();
        for (int i = 0; i < 8; i++) {
            playerHand.add(deck.draw());
        }

        // Initialize the player's jokers and round configuration
        playerJokers = new HeldJokers();
        roundConfig = new RoundConfig(300);
        roundConfig.setBossType(BossType.NONE);

        // Set up the initial round state
        roundState = new RoundState(0, 3, 3, playerJokers, deck);
    }

    @Test
    void testPlayCards_Success() throws JavatroException {
        // Test playing 3 cards successfully
        List<Integer> cardIndices = List.of(0, 1, 2);
        int initialDeckSize = deck.getRemainingCards();

        // Perform the play action and validate the results
        RoundActions.ActionResult result = RoundActions.playCards(roundState, roundConfig, cardIndices);
        assertEquals(3, result.getCards().size(), "Should have played 3 cards");
        assertEquals(8, playerHand.getHand().size(), "Player should still have 8 cards after play and draw");
        assertEquals(initialDeckSize - 3, deck.getRemainingCards(), "Deck should have 3 fewer cards");
        assertNotNull(result.getPointsEarned(), "Points should be earned for playing cards");
    }

    @Test
    void testPlayCards_NoRemainingPlays() {
        // Test behavior when no plays are remaining
        RoundState stateWithNoPlays;
        try {
            stateWithNoPlays = new RoundState(0, 3, 0, playerJokers, deck);
        } catch (JavatroException e) {
            fail();
            return;
        }
        List<Integer> cardIndices = List.of(0, 1, 2);

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.playCards(stateWithNoPlays, roundConfig, cardIndices),
            "Should throw JavatroException when no plays remain"
        );
        assertEquals(UI.RED + "No plays remaining." + UI.END, exception.getMessage());
    }

    @Test
    void testPlayCards_TooFewCards() {
        // Test playing fewer cards than the minimum required
        RoundConfig config = new RoundConfig(300);
        config.setMinHandSize(3);
        config.setMaxHandSize(5);
        List<Integer> cardIndices = List.of(0, 1);

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.playCards(roundState, config, cardIndices),
            "Should throw JavatroException when playing fewer cards than minimum"
        );
        assertEquals(UI.RED + "A poker hand must contain between 3 and 5 cards." + UI.END, 
            exception.getMessage());
    }

    @Test
    void testPlayCards_TooManyCards() {
        // Test playing more cards than the maximum allowed
        RoundConfig config = new RoundConfig(300);
        config.setMinHandSize(1);
        config.setMaxHandSize(3);
        List<Integer> cardIndices = List.of(0, 1, 2, 3, 4);

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.playCards(roundState, config, cardIndices),
            "Should throw JavatroException when playing more cards than maximum"
        );
        assertEquals(UI.RED + "A poker hand must contain between 1 and 3 cards." + UI.END, 
            exception.getMessage());
    }

    @Test
    void testPlayCards_InvalidIndices() {
        // Test playing cards with invalid indices
        List<Integer> cardIndices = List.of(0, 20);

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.playCards(roundState, roundConfig, cardIndices),
            "Should throw JavatroException when trying to play a card with an invalid index"
        );
        assertEquals(UI.RED + "Invalid index in cards to be played: 20" + UI.END, 
            exception.getMessage());
    }

    @Test
    void testPlayCards_ExactMinimumHandSize() throws JavatroException {
        // Test playing the exact minimum number of cards
        RoundConfig config = new RoundConfig(300);
        config.setMinHandSize(2);
        config.setMaxHandSize(5);
        List<Integer> cardIndices = List.of(0, 1);

        // Perform the play action and validate the results
        RoundActions.ActionResult result = RoundActions.playCards(roundState, config, cardIndices);
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.getCards().size(), "Should have played 2 cards");
    }

    @Test
    void testPlayCards_ExactMaximumHandSize() throws JavatroException {
        // Test playing the exact maximum number of cards
        RoundConfig config = new RoundConfig(300);
        config.setMinHandSize(1);
        config.setMaxHandSize(4);
        List<Integer> cardIndices = List.of(0, 1, 2, 3);

        // Perform the play action and validate the results
        RoundActions.ActionResult result = RoundActions.playCards(roundState, config, cardIndices);
        assertNotNull(result, "Result should not be null");
        assertEquals(4, result.getCards().size(), "Should have played 4 cards");
    }

    @Test
    void testPlayCards_NullParameters() {
        // Test behavior when null parameters are passed
        assertThrows(
            IllegalArgumentException.class,
            () -> RoundActions.playCards(null, roundConfig, List.of(0, 1, 2)),
            "Should throw IllegalArgumentException when state is null"
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> RoundActions.playCards(roundState, null, List.of(0, 1, 2)),
            "Should throw IllegalArgumentException when config is null"
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> RoundActions.playCards(roundState, roundConfig, null),
            "Should throw IllegalArgumentException when card indices are null"
        );
    }

    @Test
    void testDiscardCards_Success() throws JavatroException {
        // Test discarding 3 cards successfully
        List<Integer> cardIndices = List.of(0, 2, 4);
        int initialDeckSize = deck.getRemainingCards();

        // Perform the discard action and validate the results
        RoundActions.ActionResult result = RoundActions.discardCards(roundState, roundConfig, cardIndices);
        assertEquals(3, result.getCards().size(), "Should have discarded 3 cards");
        assertEquals(8, playerHand.getHand().size(), "Player should still have 8 cards after discard and draw");
        assertEquals(initialDeckSize - 3, deck.getRemainingCards(), "Deck should have 3 fewer cards");
        assertEquals(0, result.getPointsEarned(), "No points should be earned for discarding");
    }

    @Test
    void testDiscardCards_NoRemainingDiscards() {
        // Test behavior when no discards are remaining
        RoundState stateWithNoDiscards;
        try {
            stateWithNoDiscards = new RoundState(0, 0, 3, playerJokers, deck);
        } catch (JavatroException e) {
            fail("Unexpected exception during setup: " + e.getMessage());
            return;
        }
        List<Integer> cardIndices = List.of(0, 1);

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.discardCards(stateWithNoDiscards, roundConfig, cardIndices),
            "Should throw JavatroException when no discards remain"
        );
        assertEquals(UI.RED + "No remaining discards available" + UI.END, exception.getMessage());
    }

    @Test
    void testDiscardCards_TooManyCards() {
        // Test discarding more cards than allowed
        List<Integer> cardIndices = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            cardIndices.add(i);
        }

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.discardCards(roundState, roundConfig, cardIndices),
            "Should throw JavatroException when trying to discard too many cards"
        );
        assertEquals(UI.RED + "Too many cards selected for discarding" + UI.END, 
            exception.getMessage());
    }

    @Test
    void testDiscardCards_EmptyIndices() {
        // Test discarding zero cards
        List<Integer> cardIndices = List.of();

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.discardCards(roundState, roundConfig, cardIndices),
            "Should throw JavatroException when trying to discard zero cards"
        );
        assertEquals(UI.RED + "Cannot discard zero cards" + UI.END, exception.getMessage());
    }

    @Test
    void testDiscardCards_DuplicateIndices() throws JavatroException {
        // Test discarding cards with duplicate indices
        List<Integer> cardIndices = List.of(1, 1, 2, 2, 3);
        int initialDeckSize = deck.getRemainingCards();

        // Perform the discard action and validate the results
        RoundActions.ActionResult result = RoundActions.discardCards(roundState, roundConfig, cardIndices);
        assertEquals(3, result.getCards().size(), "Should have discarded 3 unique cards");
        assertEquals(initialDeckSize - 3, deck.getRemainingCards(), "Deck should have 3 fewer cards");
    }

    @Test
    void testDiscardCards_InvalidIndices() {
        // Test discarding cards with invalid indices
        List<Integer> cardIndices = List.of(0, 10);

        // Validate that an exception is thrown
        JavatroException exception = assertThrows(
            JavatroException.class,
            () -> RoundActions.discardCards(roundState, roundConfig, cardIndices),
            "Should throw JavatroException when trying to discard a card with an invalid index"
        );
        assertEquals(UI.RED + "Invalid index in cards to be discarded: 10" + UI.END, 
            exception.getMessage());
    }

    @Test
    void testDiscardCards_ExactRemainingDiscards() throws JavatroException {
        // Test discarding the exact number of remaining discards
        RoundState stateWithOneDiscard = new RoundState(0, 1, 3, playerJokers, deck);
        List<Integer> cardIndices = List.of(0);

        // Perform the discard action and validate the results
        RoundActions.ActionResult result = RoundActions.discardCards(stateWithOneDiscard, roundConfig, cardIndices);
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.getCards().size(), "Should have discarded 1 card");
    }
}