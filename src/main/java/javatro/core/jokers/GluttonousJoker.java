package javatro.core.jokers;

import javatro.core.Card;
import javatro.core.JavatroCore;
import javatro.core.Score;

/**
 * Represents a GluttonousJoker Joker.
 */

public class GluttonousJoker extends Joker{

    public GluttonousJoker() {
        super();
        this.description = "Played cards with Diamond suit give +3 Mult when scored";
        this.scoreType = ScoreType.ONCARDPLAY;
    }

    @Override
    public void interact (Score scoreClass, Card playedCard) {
        if (playedCard.suit() == Card.Suit.DIAMONDS) {
            scoreClass.totalMultiplier += 3;
        }
    }

    @Override
    public String toString() {
        return "+3 Mult Spades";
    }

}
