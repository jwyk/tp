package javatro.core;

import java.util.List;
import java.lang.Math;

/**
 * Contains the algorithm for calculating the final score and played hand.
 */

public class Score {

    /**
     * Returns the score of the played hand by calculating the value of the hand.
     */
    public int getScore(PokerHand pokerHand, List<Card> playedCardList) throws JavatroException{
        double totalChips = 0;
        double totalMultiplier = 0;

        //First add pokerHand's chip and mult base to the scores.
        totalChips = (double) pokerHand.getChips();
        totalMultiplier = (double) pokerHand.getMultiplier();

        for (Card card: playedCardList) {

        }

        Math.round(totalChips);
        Math.round(totalMultiplier);
        //Round the score and return the correct value
        totalMultiplier = (int) Math.ceil(totalChips * totalMultiplier);
        return 0;
    }
}
