package javatro.core.jokers;

import javatro.core.Card;
import javatro.core.Score;

public class LustyJoker extends Joker {

    public LustyJoker() {
        super();
        this.description = "Played cards with Heart suit give +3 Mult when scored ";
    }

    @Override
    public void interact(Score scoreClass, Card playedCard) {
        if (playedCard.suit() == Card.Suit.HEARTS) {
            scoreClass.totalMultiplier += 3;
        }
    }
}
