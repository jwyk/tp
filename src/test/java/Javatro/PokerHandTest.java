package Javatro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

class PokerHandTest {

    @Test
    void testStraightFlushTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.NINE, Card.Suit.SPADES),
                new Card(Card.Rank.TEN, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.QUEEN, Card.Suit.SPADES),
                new Card(Card.Rank.KING, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Straight Flush", result.handName());
    }

    @Test
    void testStraightFlushFalseMixedSuits() {
        List<Card> hand = List.of(
                new Card(Card.Rank.NINE, Card.Suit.SPADES),
                new Card(Card.Rank.TEN, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.QUEEN, Card.Suit.SPADES),
                new Card(Card.Rank.KING, Card.Suit.DIAMONDS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Straight Flush", result.handName());
    }

    @Test
    void testStraightFlushFalseNotInSequence() {
        List<Card> hand = List.of(
                new Card(Card.Rank.NINE, Card.Suit.SPADES),
                new Card(Card.Rank.TEN, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.QUEEN, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Straight Flush", result.handName());
    }

    @Test
    void testRoyalFlushTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.TEN, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.QUEEN, Card.Suit.SPADES),
                new Card(Card.Rank.KING, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Royal Flush", result.handName());
    }

    @Test
    void testRoyalFlushFalseAceLow() {
        List<Card> hand = List.of(
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.TWO, Card.Suit.SPADES),
                new Card(Card.Rank.THREE, Card.Suit.SPADES),
                new Card(Card.Rank.FOUR, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Royal Flush", result.handName());
    }

    @Test
    void testFlushTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.TWO, Card.Suit.SPADES),
                new Card(Card.Rank.NINE, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Flush", result.handName());
    }

    @Test
    void testFlushFalseMixedSuits() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.DIAMONDS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.TWO, Card.Suit.SPADES),
                new Card(Card.Rank.NINE, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Flush", result.handName());
    }

    @Test
    void testFlushFalseLessThan5Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.TWO, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Flush", result.handName());
    }

    @Test
    void testHighCardTrue() {
        List<Card> hand = List.of(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("High Card", result.handName());
    }

    @Test
    void testStraightTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.SPADES),
                new Card(Card.Rank.SEVEN, Card.Suit.CLUBS),
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.NINE, Card.Suit.DIAMONDS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Straight", result.handName());
    }

    @Test
    void testStraightFalseNotInSequence() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.SPADES),
                new Card(Card.Rank.SEVEN, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Straight", result.handName());
    }

    @Test
    void testStraightFalseMixedSequence() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.NINE, Card.Suit.SPADES),
                new Card(Card.Rank.TEN, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Straight", result.handName());
    }

    @Test
    void testStraightFalseLessThan5Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.SIX, Card.Suit.SPADES),
                new Card(Card.Rank.SEVEN, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Straight", result.handName());
    }

    @Test
    void testFullHouseTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Full House", result.handName());
    }

    @Test
    void testFullHouseFalseTwoPair() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Full House", result.handName());
    }

    @Test
    void testFullHouseFalseLessThan5Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Full House", result.handName());
    }

    @Test
    void testFullHouseFalseThreeOfAKind() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Full House", result.handName());
    }

    @Test
    void testThreeOfAKindTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Three of a Kind", result.handName());
    }

    @Test
    void testThreeOfAKindTrue3Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.TEN, Card.Suit.SPADES),
                new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Three of a Kind", result.handName());
    }

    @Test
    void testThreeOfAKindFalseFullHouse() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Three of a Kind", result.handName());
    }

    @Test
    void testFourOfAKindTrue5Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS),
                new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                new Card(Card.Rank.EIGHT, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Four of a Kind", result.handName());
    }

    @Test
    void testFourOfAKindTrue4Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS),
                new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                new Card(Card.Rank.EIGHT, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Four of a Kind", result.handName());
    }

    @Test
    void testPairTrue5Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.SEVEN, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Pair", result.handName());
    }

    @Test
    void testPairFalseTwoPair() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS),
                new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.SEVEN, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Pair", result.handName());
    }

    @Test
    void testPairTrue2Cards() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Pair", result.handName());
    }

    @Test
    void testPairFalseFullHouse() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Pair", result.handName());
    }

    @Test
    void testPairFalseFourOfAKind() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS),
                new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                new Card(Card.Rank.EIGHT, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Pair", result.handName());
    }

    @Test
    void testTwoPairTrue() {
        List<Card> hand = List.of(
                new Card(Card.Rank.EIGHT, Card.Suit.SPADES),
                new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS),
                new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                new Card(Card.Rank.ACE, Card.Suit.SPADES),
                new Card(Card.Rank.SEVEN, Card.Suit.SPADES));
        HandResult result = PokerHand.evaluateHand(hand);
        assertEquals("Two Pair", result.handName());
    }

    @Test
    void testTwoPairFalseFullHouse() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                new Card(Card.Rank.JACK, Card.Suit.SPADES),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Two Pair", result.handName());
    }

    @Test
    void testTwoPairFalseFourOfAKind() {
        List<Card> hand = List.of(
                new Card(Card.Rank.FIVE, Card.Suit.SPADES),
                new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS),
                new Card(Card.Rank.FIVE, Card.Suit.CLUBS),
                new Card(Card.Rank.FIVE, Card.Suit.HEARTS),
                new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        HandResult result = PokerHand.evaluateHand(hand);
        assertNotEquals("Two Pair", result.handName());
    }
}
