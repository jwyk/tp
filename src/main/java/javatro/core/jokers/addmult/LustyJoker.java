package javatro.core.jokers.addmult;

import javatro.core.Card;
import javatro.core.Score;
import javatro.core.jokers.Joker;

/** Represents a LustyJoker Joker. */
public class LustyJoker extends Joker {

    public LustyJoker() {
        super();
        this.description = "Played cards with Heart suit give +3 Mult when scored ";
        this.scoreType = ScoreType.ONCARDPLAY;
    }

    @Override
    public void interact(Score scoreClass, Card playedCard) {
        if (playedCard.suit() == Card.Suit.HEARTS) {
            scoreClass.totalMultiplier += 3;
        }
    }
}
