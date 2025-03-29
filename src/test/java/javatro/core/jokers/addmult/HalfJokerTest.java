package javatro.core.jokers.addmult;

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
import javatro.core.jokers.HeldJokers;
import javatro.core.jokers.Joker;
import javatro.core.jokers.addmult.CounterJoker;
import javatro.core.jokers.addmult.GluttonousJoker;
import javatro.core.jokers.addmult.HalfJoker;
import javatro.core.jokers.addmult.WrathfulJoker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HalfJokerTest {
    private static List<Card> playedCardList;
    private static HeldJokers heldJokers;
    private static HandResult handResult;
    private static PokerHand result;
    private static Card cardOne;
    private static Card cardTwo;
    private static Card cardThree;
    private static Card cardFour;
    private static Card cardFive;
    private static Joker halfJoker;
    /** Initialize a test run. */
    @BeforeEach
    void init() throws JavatroException {
        halfJoker = new HalfJoker();
        heldJokers = new HeldJokers();
        heldJokers.add(halfJoker);
        cardOne = new Card(NINE, DIAMONDS);
        cardTwo = new Card(NINE, DIAMONDS);
        cardThree = new Card(TEN, HEARTS);
        cardFour = new Card(TEN, HEARTS);
    }

    /**
     * Test that a hand played triggers joker effects correctly and has the right score.
     */
    void assertScoreEquals(HeldJokers currentJokers, int expectedScore) throws JavatroException {
        result = HandResult.evaluateHand(playedCardList);
        Score scoreObject = new Score();
        long finalScore = scoreObject.getScore(result, playedCardList, currentJokers);
        assertEquals(expectedScore, finalScore);
    }

    /**
     * Test that a hand with 3 cards played correctly triggers the HalfJoker.
     */
    @Test
    void testHalfJokerCorrect() throws JavatroException {
        playedCardList =
                List.of(
                        cardOne,
                        cardTwo,
                        cardThree
                );
        assertScoreEquals(heldJokers, 836);
    }

    /**
     * Test that a hand with 4 cards does not trigger the HalfJoker.
     */
    @Test
    void testHalfJokerDisabled() throws JavatroException {
        playedCardList =
                List.of(
                        cardOne,
                        cardTwo,
                        cardThree,
                        cardFour
                );
        assertScoreEquals(heldJokers, 116);
    }

}
