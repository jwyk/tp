package javatro.core.jokers;

import javatro.core.Card;
import javatro.core.Score;

/** Represents a WrathfulJoker Joker. */
public class WrathfulJoker extends Joker {

    public WrathfulJoker() {
        super();
        this.description = "Played cards with Spade suit give +3 Mult when scored ";
        this.scoreType = ScoreType.ONCARDPLAY;
    }

    @Override
    public void interact(Score scoreClass, Card playedCard) {
        if (playedCard.suit() == Card.Suit.SPADES) {
            scoreClass.totalMultiplier += 3;
        }
    }
}
