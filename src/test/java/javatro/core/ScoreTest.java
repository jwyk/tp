package javatro.core;

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javatro.core.jokers.HeldJokers;
import javatro.core.Score;
import javatro.display.UI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//public class RoundTest {
//    private static final String INVALIDPLAYEDHANDERROR =
//            UI.WARNING + UI.RED + "A poker hand must contain between 1 and 5 cards." + UI.END;
//    private static final String INVALIDPLAYSPERROUND =
//            UI.WARNING + UI.RED + "Number of plays per round must be greater than 0." + UI.END;
//    private static final String INVALIDBLINDSCORE =
//            UI.WARNING + UI.RED + "Blind score must be greater than or equal to 0." + UI.END;
//    private static final String INVALIDDECK = UI.WARNING + UI.RED + "Deck cannot be null." + UI.END;
//    private static final String INVALIDPLAYSREMAINING =
//            UI.WARNING + UI.RED + "No plays remaining." + UI.END;
//}

public class ScoreTest {
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
    void init() throws JavatroException{
        heldJokers = new HeldJokers();
        playedCardList =
                List.of(
                        new Card(NINE, DIAMONDS),
                        new Card(TEN, HEARTS),
                        new Card(JACK, CLUBS),
                        new Card(QUEEN, SPADES),
                        new Card(KING, SPADES));
//        result = HandResult.evaluateHand(playedCardList);
    }

    /** Test that each hand played gives the correct score. */
    @Test
    void testScore() throws JavatroException {

        result = HandResult.evaluateHand(playedCardList);
        Score scoreObject = new Score();
        long finalScore = scoreObject.getScore(result,playedCardList, heldJokers);
        assertEquals(316, finalScore);
    }

}
