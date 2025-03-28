package javatro.core.jokers;

import javatro.core.Card;
import javatro.core.JavatroCore;
import javatro.core.Score;

/**
 * Represents a GreedyJoker Joker
 */

public class GreedyJoker extends Joker{

    public GreedyJoker() {
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

}
