package javatro.core.jokers;

import static javatro.core.Card.Rank.JACK;
import static javatro.core.Card.Rank.KING;
import static javatro.core.Card.Rank.NINE;
import static javatro.core.Card.Rank.QUEEN;
import static javatro.core.Card.Rank.TEN;
import static javatro.core.Card.Suit.CLUBS;
import static javatro.core.Card.Suit.DIAMONDS;
import static javatro.core.Card.Suit.HEARTS;
import static javatro.core.Card.Suit.SPADES;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javatro.core.Card;
import javatro.core.HandResult;
import javatro.core.JavatroException;
import javatro.core.PokerHand;
import javatro.core.Score;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HeldJokersTest {
    private static List<Card> playedCardList;
    private static HeldJokers heldJokers;
    private static HandResult handResult;
    private static PokerHand result;
    private static Card cardOne;
    private static Card cardTwo;
    private static Card cardThree;
    private static Card cardFour;
    private static Card cardFive;

    /** Initialize a test run. */
    @BeforeEach
    void init() throws JavatroException {
        heldJokers = new HeldJokers();
        playedCardList =
                List.of(
                        new Card(NINE, DIAMONDS),
                        new Card(TEN, HEARTS),
                        new Card(JACK, CLUBS),
                        new Card(QUEEN, SPADES),
                        new Card(KING, SPADES));
    }

    /**
     * Test that a normal HeldJokers cannot add more than 5 Jokers without exceeding the maximum
     * limit.
     */
    @Test
    void testIllegalAdd() {
        Joker gluttonousJoker = new GluttonousJoker();
        try {
            for (int i = 0; i < 10; i++) {
                heldJokers.add(gluttonousJoker);
            }
            fail();
        } catch (JavatroException e) {
            assertEquals(JavatroException.exceedsMaxJokers().getMessage(), e.getMessage());
        }
    }

    /**
     * Test that a hand played triggers multiple joker effects correctly and has the right score.
     */
    @Test
    void testMultipleJokers() throws JavatroException {
        Joker gluttonousJoker = new GluttonousJoker();
        Joker counterJoker = new CounterJoker(1);
        heldJokers.add(counterJoker);
        heldJokers.add(gluttonousJoker);
        result = HandResult.evaluateHand(playedCardList);
        Score scoreObject = new Score();
        long finalScore = scoreObject.getScore(result, playedCardList, heldJokers);
        assertEquals(1027, finalScore);
    }
}
