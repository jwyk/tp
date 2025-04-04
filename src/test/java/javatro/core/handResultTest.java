// @@author Markneoneo
package javatro.core;

import static javatro.core.Card.Rank.ACE;
import static javatro.core.Card.Rank.EIGHT;
import static javatro.core.Card.Rank.FIVE;
import static javatro.core.Card.Rank.FOUR;
import static javatro.core.Card.Rank.JACK;
import static javatro.core.Card.Rank.KING;
import static javatro.core.Card.Rank.NINE;
import static javatro.core.Card.Rank.QUEEN;
import static javatro.core.Card.Rank.SEVEN;
import static javatro.core.Card.Rank.SIX;
import static javatro.core.Card.Rank.TEN;
import static javatro.core.Card.Rank.THREE;
import static javatro.core.Card.Rank.TWO;
import static javatro.core.Card.Suit.CLUBS;
import static javatro.core.Card.Suit.DIAMONDS;
import static javatro.core.Card.Suit.HEARTS;
import static javatro.core.Card.Suit.SPADES;
import static javatro.core.PokerHand.HandType.FIVE_OF_A_KIND;
import static javatro.core.PokerHand.HandType.FLUSH_FIVE;
import static javatro.core.PokerHand.HandType.FLUSH_HOUSE;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * This class contains comprehensive unit tests for the {@link HandResult} class.
 * It verifies the correct identification and ranking of various poker hand types,
 * including standard poker hands and special hand types like Flush Five and Flush House.
 */
class HandResultTest {

    /* ==================== STRAIGHT FLUSH TESTS ==================== */

    /**
     * Tests that a straight flush is correctly identified.
     * A straight flush is a hand that contains five cards of sequential rank, all the same suit.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightFlushTrue() throws JavatroException {
        // Arrange: Create a straight flush hand (9-K all spades)
        List<Card> hand =
                List.of(
                        new Card(NINE, SPADES),
                        new Card(TEN, SPADES),
                        new Card(JACK, SPADES),
                        new Card(QUEEN, SPADES),
                        new Card(KING, SPADES));

        // Act: Evaluate the hand
        PokerHand result = HandResult.evaluateHand(hand);

        // Assert: Verify correct hand type identification
        assertEquals("Straight Flush", result.getHandName());
    }

    /**
     * Tests that a hand with mixed suits is not identified as a straight flush.
     * The hand should be identified as a straight instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightFlushFalseMixedSuits() throws JavatroException {
        // Arrange: Create a straight with mixed suits
        List<Card> hand =
                List.of(
                        new Card(NINE, SPADES),
                        new Card(TEN, SPADES),
                        new Card(JACK, SPADES),
                        new Card(QUEEN, SPADES),
                        new Card(KING, DIAMONDS));

        // Act: Evaluate the hand
        PokerHand result = HandResult.evaluateHand(hand);

        // Assert: Verify it's not a straight flush but is a straight
        assertNotEquals("Straight Flush", result.getHandName());
        assertEquals("Straight", result.getHandName());
    }

    /**
     * Tests that a hand with cards not in sequence is not identified as a straight flush. The hand
     * should be identified as a flush instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightFlushFalseNotInSequence() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(NINE, SPADES),
                        new Card(TEN, SPADES),
                        new Card(JACK, SPADES),
                        new Card(QUEEN, SPADES),
                        new Card(ACE, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Straight Flush", result.getHandName());
        assertEquals("Flush", result.getHandName());
    }

    /* ==================== ROYAL FLUSH TESTS ==================== */

    /**
     * Tests that a royal flush is correctly identified. A royal flush is a hand that contains the
     * Ace, King, Queen, Jack, and Ten of the same suit.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testRoyalFlushTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(TEN, SPADES),
                        new Card(JACK, SPADES),
                        new Card(QUEEN, SPADES),
                        new Card(KING, SPADES),
                        new Card(ACE, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Royal Flush", result.getHandName());
    }

    /**
     * Tests that a hand with an Ace as a low card is not identified as a royal flush. The hand
     * should be identified as a straight flush instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testRoyalFlushFalseAceLow() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, SPADES),
                        new Card(TWO, SPADES),
                        new Card(THREE, SPADES),
                        new Card(FOUR, SPADES),
                        new Card(FIVE, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Royal Flush", result.getHandName());
        assertEquals("Straight Flush", result.getHandName());
    }

    /* ==================== FLUSH TESTS ==================== */

    /**
     * Tests that a flush is correctly identified. A flush is a hand that contains five cards of the
     * same suit, not in sequence.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, SPADES),
                        new Card(ACE, SPADES),
                        new Card(TWO, SPADES),
                        new Card(NINE, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Flush", result.getHandName());
    }

    /**
     * Tests that a hand with mixed suits is not identified as a flush. The hand should be
     * identified as a high card instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushFalseMixedSuits() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, DIAMONDS),
                        new Card(ACE, SPADES),
                        new Card(TWO, SPADES),
                        new Card(NINE, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Flush", result.getHandName());
        assertEquals("High Card", result.getHandName());
    }

    /**
     * Tests that a hand with less than five cards is not identified as a flush. The hand should be
     * identified as a high card instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushFalseLessThan5Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, SPADES),
                        new Card(ACE, SPADES),
                        new Card(TWO, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Flush", result.getHandName());
        assertEquals("High Card", result.getHandName());
    }

    /* ==================== HIGH CARD TESTS ==================== */

    /**
     * Tests that a high card is correctly identified. A high card is a hand that does not fall into
     * any other category.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testHighCardTrue() throws JavatroException {
        List<Card> hand = List.of(new Card(ACE, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("High Card", result.getHandName());
    }

    /* ==================== STRAIGHT TESTS ==================== */

    /**
     * Tests that a straight is correctly identified. A straight is a hand that contains five cards
     * of sequential rank, not all the same suit.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, SPADES),
                        new Card(SEVEN, CLUBS),
                        new Card(EIGHT, SPADES),
                        new Card(NINE, DIAMONDS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Straight", result.getHandName());
    }

    /**
     * Tests that a hand with cards not in sequence is not identified as a straight. The hand should
     * be identified as a high card instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightFalseNotInSequence() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, SPADES),
                        new Card(SEVEN, SPADES),
                        new Card(EIGHT, SPADES),
                        new Card(JACK, DIAMONDS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Straight", result.getHandName());
        assertEquals("High Card", result.getHandName());
    }

    /**
     * Tests that a hand with mixed sequence is not identified as a straight. The hand should be
     * identified as a high card instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightFalseMixedSequence() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, SPADES),
                        new Card(EIGHT, SPADES),
                        new Card(NINE, SPADES),
                        new Card(TEN, DIAMONDS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Straight", result.getHandName());
        assertEquals("High Card", result.getHandName());
    }

    /**
     * Tests that a hand with less than five cards is not identified as a straight. The hand should
     * be identified as a high card instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testStraightFalseLessThan5Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(SIX, SPADES),
                        new Card(SEVEN, SPADES),
                        new Card(EIGHT, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Straight", result.getHandName());
        assertEquals("High Card", result.getHandName());
    }

    /* ==================== FULL HOUSE TESTS ==================== */

    /**
     * Tests that a full house is correctly identified. A full house is a hand that contains three
     * cards of one rank and two cards of another rank.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFullHouseTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, CLUBS),
                        new Card(JACK, SPADES),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Full House", result.getHandName());
    }

    /**
     * Tests that a hand with two pairs is not identified as a full house. The hand should be
     * identified as two pairs instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFullHouseFalseTwoPair() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(ACE, CLUBS),
                        new Card(JACK, SPADES),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Full House", result.getHandName());
        assertEquals("Two Pair", result.getHandName());
    }

    /**
     * Tests that a hand with less than five cards is not identified as a full house. The hand
     * should be identified as two pairs instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFullHouseFalseLessThan5Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(JACK, SPADES),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Full House", result.getHandName());
        assertEquals("Two Pair", result.getHandName());
    }

    /**
     * Tests that a hand with three of a kind is not identified as a full house. The hand should be
     * identified as three of a kind instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFullHouseFalseThreeOfAKind() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, HEARTS),
                        new Card(JACK, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Full House", result.getHandName());
        assertEquals("Three of a Kind", result.getHandName());
    }

    /* ==================== THREE OF A KIND TESTS ==================== */

    /**
     * Tests that three of a kind is correctly identified. Three of a kind is a hand that contains
     * three cards of one rank and two cards of two different ranks.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testThreeOfAKindTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, HEARTS),
                        new Card(JACK, SPADES),
                        new Card(ACE, CLUBS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Three of a Kind", result.getHandName());
    }

    /**
     * Tests that three of a kind is correctly identified with only three cards.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testThreeOfAKindTrue3Cards() throws JavatroException {
        List<Card> hand =
                List.of(new Card(TEN, SPADES), new Card(TEN, DIAMONDS), new Card(TEN, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Three of a Kind", result.getHandName());
    }

    /**
     * Tests that a full house is not identified as three of a kind. The hand should be identified
     * as a full house instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testThreeOfAKindFalseFullHouse() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, CLUBS),
                        new Card(JACK, SPADES),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Three of a Kind", result.getHandName());
        assertEquals("Full House", result.getHandName());
    }

    /* ==================== FOUR OF A KIND TESTS ==================== */

    /**
     * Tests that four of a kind is correctly identified with five cards. Four of a kind is a hand
     * that contains four cards of one rank and one card of another rank.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFourOfAKindTrue5Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(EIGHT, SPADES),
                        new Card(EIGHT, DIAMONDS),
                        new Card(EIGHT, CLUBS),
                        new Card(EIGHT, HEARTS),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Four of a Kind", result.getHandName());
    }

    /** Tests that four of a kind is correctly identified with four cards.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFourOfAKindTrue4Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(EIGHT, SPADES),
                        new Card(EIGHT, DIAMONDS),
                        new Card(EIGHT, CLUBS),
                        new Card(EIGHT, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Four of a Kind", result.getHandName());
    }

    /* ==================== PAIR TESTS ==================== */

    /**
     * Tests that a pair is correctly identified with five cards. A pair is a hand that contains two
     * cards of one rank and three cards of three different ranks.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testPairTrue5Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(EIGHT, SPADES),
                        new Card(EIGHT, DIAMONDS),
                        new Card(FIVE, CLUBS),
                        new Card(ACE, SPADES),
                        new Card(SEVEN, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Pair", result.getHandName());
    }

    /**
     * Tests that a hand with two pairs is not identified as a single pair. The hand should be
     * identified as two pairs instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testPairFalseTwoPair() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(EIGHT, SPADES),
                        new Card(EIGHT, DIAMONDS),
                        new Card(ACE, CLUBS),
                        new Card(ACE, SPADES),
                        new Card(SEVEN, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Pair", result.getHandName());
        assertEquals("Two Pair", result.getHandName());
    }

    /** Tests that a pair is correctly identified with two cards.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testPairTrue2Cards() throws JavatroException {
        List<Card> hand = List.of(new Card(EIGHT, SPADES), new Card(EIGHT, DIAMONDS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Pair", result.getHandName());
    }

    /**
     * Tests that a full house is not identified as a pair. The hand should be identified as a full
     * house instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testPairFalseFullHouse() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, CLUBS),
                        new Card(JACK, SPADES),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Pair", result.getHandName());
        assertEquals("Full House", result.getHandName());
    }

    /**
     * Tests that four of a kind is not identified as a pair. The hand should be identified as four
     * of a kind instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testPairFalseFourOfAKind() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(EIGHT, SPADES),
                        new Card(EIGHT, DIAMONDS),
                        new Card(EIGHT, CLUBS),
                        new Card(EIGHT, HEARTS),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Pair", result.getHandName());
        assertEquals("Four of a Kind", result.getHandName());
    }

    /* ==================== TWO PAIR TESTS ==================== */

    /**
     * Tests that two pairs are correctly identified. Two pairs is a hand that contains two cards of
     * one rank, two cards of another rank, and one card of a third rank.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testTwoPairTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(EIGHT, SPADES),
                        new Card(EIGHT, DIAMONDS),
                        new Card(ACE, CLUBS),
                        new Card(ACE, SPADES),
                        new Card(SEVEN, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals("Two Pair", result.getHandName());
    }

    /**
     * Tests that a full house is not identified as two pairs. The hand should be identified as a
     * full house instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testTwoPairFalseFullHouse() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, CLUBS),
                        new Card(JACK, SPADES),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Two Pair", result.getHandName());
        assertEquals("Full House", result.getHandName());
    }

    /**
     * Tests that four of a kind is not identified as two pairs. The hand should be identified as
     * four of a kind instead.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testTwoPairFalseFourOfAKind() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(FIVE, SPADES),
                        new Card(FIVE, DIAMONDS),
                        new Card(FIVE, CLUBS),
                        new Card(FIVE, HEARTS),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals("Two Pair", result.getHandName());
        assertEquals("Four of a Kind", result.getHandName());
    }

    /* ==================== FLUSH FIVE TESTS ==================== */

    /**
     * Tests that a Flush Five is correctly identified (all 5 cards identical in rank and suit).
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushFiveTrue() throws JavatroException {
        // Arrange: Create a Flush Five hand (5 identical Ace of Hearts)
        List<Card> hand = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, HEARTS),
                new Card(ACE, HEARTS),
                new Card(ACE, HEARTS),
                new Card(ACE, HEARTS)
        );

        // Act: Evaluate the hand
        PokerHand result = HandResult.evaluateHand(hand);

        // Assert: Verify correct hand type identification
        assertEquals(FLUSH_FIVE, result.handType());
        assertEquals("Flush Five", result.getHandName());
    }

    /**
     * Tests that a hand with same rank but different suits is not a Flush Five.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushFiveFalseDifferentSuits() throws JavatroException {
        // Arrange: Create a Five of a Kind hand (same rank, different suits)
        List<Card> hand = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(ACE, CLUBS),
                new Card(ACE, SPADES),
                new Card(ACE, HEARTS)
        );

        // Act: Evaluate the hand
        PokerHand result = HandResult.evaluateHand(hand);

        // Assert: Verify it's not Flush Five but is Five of a Kind
        assertNotEquals(FLUSH_FIVE, result.handType());
        assertEquals(FIVE_OF_A_KIND, result.handType());
    }

    /**
     * Tests that a hand with same suit but different ranks is not a Flush Five.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushFiveFalseDifferentRanks() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(KING, HEARTS),
                        new Card(QUEEN, HEARTS),
                        new Card(JACK, HEARTS),
                        new Card(TEN, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FLUSH_FIVE, result.handType());
        assertEquals("Royal Flush", result.getHandName());
    }

    /**
     * Tests that a hand with less than 5 cards cannot be a Flush Five.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushFiveFalseLessThan5Cards() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FLUSH_FIVE, result.handType());
        assertEquals("Four of a Kind", result.getHandName());
    }

    /* ==================== FIVE OF A KIND TESTS ==================== */

    /**
     * Tests that Five of a Kind is correctly identified (all 5 cards same rank, suits can differ).
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFiveOfAKindTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, DIAMONDS),
                        new Card(ACE, CLUBS),
                        new Card(ACE, SPADES),
                        new Card(ACE, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals(FIVE_OF_A_KIND, result.handType());
        assertEquals("Five of a Kind", result.getHandName());
    }

    /**
     * Tests that a hand with only 4 of a kind is not Five of a Kind.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFiveOfAKindFalseFourOfAKind() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, DIAMONDS),
                        new Card(ACE, CLUBS),
                        new Card(ACE, SPADES),
                        new Card(KING, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FIVE_OF_A_KIND, result.handType());
        assertEquals("Four of a Kind", result.getHandName());
    }

    /**
     * Tests that a Flush Five is not mistakenly identified as Five of a Kind.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFiveOfAKindFalseFlushFive() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FIVE_OF_A_KIND, result.handType());
        assertEquals(FLUSH_FIVE, result.handType());
    }

    /* ==================== FLUSH HOUSE TESTS ==================== */

    /** Tests that Flush House is correctly identified (full house with all cards same suit).
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushHouseTrue() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(KING, HEARTS),
                        new Card(KING, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertEquals(FLUSH_HOUSE, result.handType());
        assertEquals("Flush House", result.getHandName());
    }

    /** Tests that a regular full house with mixed suits is not a Flush House.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushHouseFalseMixedSuits() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, DIAMONDS),
                        new Card(ACE, CLUBS),
                        new Card(KING, HEARTS),
                        new Card(KING, SPADES));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FLUSH_HOUSE, result.handType());
        assertEquals("Full House", result.getHandName());
    }

    /** Tests that a flush without the full house composition is not a Flush House.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushHouseFalseNotFullHouse() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(KING, HEARTS),
                        new Card(QUEEN, HEARTS),
                        new Card(JACK, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FLUSH_HOUSE, result.handType());
        assertEquals("Flush", result.getHandName());
    }

    /** Tests that a Flush Five is not mistakenly identified as Flush House.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testFlushHouseFalseFlushFive() throws JavatroException {
        List<Card> hand =
                List.of(
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS),
                        new Card(ACE, HEARTS));
        PokerHand result = HandResult.evaluateHand(hand);
        assertNotEquals(FLUSH_HOUSE, result.handType());
        assertEquals(FLUSH_FIVE, result.handType());
    }

    /* ==================== HAND PRIORITY TESTS ==================== */

    // Enum values are ordered from strongest to weakest in declaration
    // ordinal() returns the position (0-based index) in the enum declaration
    // compareTo() returns negative when the first is "less than" (stronger than) the second
    /**
     * Comprehensive test that verifies the correct priority ordering of all poker hand types.
     * This test ensures that stronger hands properly outrank weaker ones according to the
     * defined priority values in HandType.
     *
     * @throws JavatroException if there's an error in hand evaluation
     */
    @Test
    void testAllHandPriorities() throws JavatroException {
        // Create sample hands for each hand type in order from strongest to weakest
        List<TestHand> testHands = List.of(
                new TestHand(
                        // 1. Flush Five (all cards identical)
                        "Flush Five",
                        FLUSH_FIVE,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, HEARTS),
                                new Card(ACE, HEARTS),
                                new Card(ACE, HEARTS),
                                new Card(ACE, HEARTS)
                        )
                ),
                new TestHand(
                        // 2. Flush House (full house with all cards same suit)
                        "Flush House",
                        FLUSH_HOUSE,
                        List.of(
                                new Card(KING, DIAMONDS),
                                new Card(KING, DIAMONDS),
                                new Card(KING, DIAMONDS),
                                new Card(QUEEN, DIAMONDS),
                                new Card(QUEEN, DIAMONDS))
                ),
                new TestHand(
                        // 3. Five of a Kind (all cards same rank)
                        "Five of a Kind",
                        FIVE_OF_A_KIND,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, DIAMONDS),
                                new Card(ACE, CLUBS),
                                new Card(ACE, SPADES),
                                new Card(ACE, HEARTS))
                ),
                new TestHand(
                        // 4. Royal Flush (A-K-Q-J-10 same suit)
                        "Royal Flush",
                        PokerHand.HandType.ROYAL_FLUSH,
                        List.of(
                                new Card(ACE, SPADES),
                                new Card(KING, SPADES),
                                new Card(QUEEN, SPADES),
                                new Card(JACK, SPADES),
                                new Card(TEN, SPADES))
                ),
                new TestHand(
                        // 5. Straight Flush (sequential same suit, not royal)
                        "Straight Flush",
                        PokerHand.HandType.STRAIGHT_FLUSH,
                        List.of(
                                new Card(NINE, HEARTS),
                                new Card(TEN, HEARTS),
                                new Card(JACK, HEARTS),
                                new Card(QUEEN, HEARTS),
                                new Card(KING, HEARTS))
                ),
                new TestHand(
                        // 6. Four of a kind
                        "Four of a Kind",
                        PokerHand.HandType.FOUR_OF_A_KIND,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, DIAMONDS),
                                new Card(ACE, CLUBS),
                                new Card(ACE, SPADES),
                                new Card(KING, HEARTS))
                ),
                new TestHand(
                        // 7. Full House
                        "Full House",
                        PokerHand.HandType.FULL_HOUSE,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, DIAMONDS),
                                new Card(ACE, CLUBS),
                                new Card(KING, SPADES),
                                new Card(KING, HEARTS))
                ),
                new TestHand(
                        // 8. Flush
                        "Flush",
                        PokerHand.HandType.FLUSH,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(TWO, HEARTS),
                                new Card(FIVE, HEARTS),
                                new Card(NINE, HEARTS),
                                new Card(QUEEN, HEARTS))
                ),
                new TestHand(
                        // 9. Straight
                        "Straight",
                        PokerHand.HandType.STRAIGHT,
                        List.of(
                                new Card(FIVE, SPADES),
                                new Card(SIX, HEARTS),
                                new Card(SEVEN, DIAMONDS),
                                new Card(EIGHT, CLUBS),
                                new Card(NINE, SPADES))
                ),
                new TestHand(
                        // 10. Three of a Kind
                        "Three of a Kind",
                        PokerHand.HandType.THREE_OF_A_KIND,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, DIAMONDS),
                                new Card(ACE, CLUBS),
                                new Card(KING, SPADES),
                                new Card(QUEEN, HEARTS))
                ),
                new TestHand(
                        // 11. Two Pair
                        "Two Pair",
                        PokerHand.HandType.TWO_PAIR,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, DIAMONDS),
                                new Card(KING, CLUBS),
                                new Card(KING, SPADES),
                                new Card(QUEEN, HEARTS))
                ),
                new TestHand(
                        // 12. Pair
                        "Pair",
                        PokerHand.HandType.PAIR,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(ACE, DIAMONDS),
                                new Card(KING, CLUBS),
                                new Card(QUEEN, SPADES),
                                new Card(JACK, HEARTS))
                ),
                new TestHand(
                        // 13. High Card
                        "High Card",
                        PokerHand.HandType.HIGH_CARD,
                        List.of(
                                new Card(ACE, HEARTS),
                                new Card(KING, DIAMONDS),
                                new Card(QUEEN, CLUBS),
                                new Card(JACK, SPADES),
                                new Card(NINE, HEARTS))
                )
        );

        // Evaluate all hands
        List<PokerHand> evaluatedHands = testHands.stream()
                .map(testHand -> {
                    try {
                        PokerHand result = HandResult.evaluateHand(testHand.cards());
                        assertEquals(testHand.expectedType(), result.handType(),
                                "Hand " + testHand.name() + " was incorrectly identified");
                        return result;
                    } catch (JavatroException e) {
                        throw new RuntimeException("Hand evaluation failed for " + testHand.name(), e);
                    }
                })
                .toList();

        // Verify each hand is stronger than all subsequent hands
        for (int i = 0; i < evaluatedHands.size(); i++) {
            PokerHand currentHand = evaluatedHands.get(i);

            // Compare against all weaker hands
            for (int j = i + 1; j < evaluatedHands.size(); j++) {
                PokerHand weakerHand = evaluatedHands.get(j);

                assertTrue(
                        currentHand.handType().compareTo(weakerHand.handType()) < 0,
                        String.format("%s should be stronger than %s but isn't",
                                testHands.get(i).name(),
                                testHands.get(j).name())
                );

                assertTrue(
                        currentHand.handType().ordinal() < weakerHand.handType().ordinal(),
                        String.format("%s should have lower ordinal than %s",
                                testHands.get(i).name(),
                                testHands.get(j).name())
                );
            }
        }

        // Additional verification of priority values
        for (int i = 0; i < evaluatedHands.size() - 1; i++) {
            PokerHand current = evaluatedHands.get(i);
            PokerHand next = evaluatedHands.get(i + 1);

            assertTrue(
                    current.handType().ordinal() < next.handType().ordinal(),
                    String.format("%s (priority %d) should have higher priority than %s (priority %d)",
                            testHands.get(i).name(),
                            current.handType().ordinal(),
                            testHands.get(i+1).name(),
                            next.handType().ordinal())
            );
        }
    }

    /**
     * Helper record to hold test hand information.
     *
     * @param name The name of the hand type being tested
     * @param expectedType The expected HandType enum value
     * @param cards The cards that make up this test hand
     */
    private record TestHand(String name, PokerHand.HandType expectedType, List<Card> cards) {}
}
