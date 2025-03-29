package javatro.core.jokers.addmult;

import javatro.core.Card;
import javatro.core.Score;
import javatro.core.jokers.Joker;

/** Represents a HalfJoker Joker. */
public class HalfJoker extends Joker {
    public HalfJoker() {
        super();
        this.description = "+20 Mult if played hand has 3 or fewer cards.";
        this.scoreType = ScoreType.AFTERHANDPLAY;
    }

    @Override
    public void interact(Score scoreClass, Card playedCard) {
        if (scoreClass.playedCardsList.size() <= 3) {
            scoreClass.totalMultiplier += 20;
        }
    }
}
