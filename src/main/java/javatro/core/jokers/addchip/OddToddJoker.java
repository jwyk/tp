package javatro.core.jokers.addchip;

import javatro.core.Card;
import javatro.core.Score;
import javatro.core.jokers.Joker;

/** Represents a OddToddJoker Joker */
public class OddToddJoker extends Joker {

    public OddToddJoker(int numberOfJokers) {
        super();
        this.description =
                "+3 Mult for each Joker card held." + "Current Multiplier: " + numberOfJokers;
        this.scoreType = ScoreType.ONCARDPLAY;
    }

    @Override
    public void interact(Score scoreClass, Card playedCard) {
        this.description = "Played cards with odd rank gives +31 Chips when scored [A,9,7,5,3].";
        if ((playedCard.getChips()) % 2 != 0) {
            scoreClass.totalChips += 31;
        }
    }

    @Override
    public String toString() {
        return "+31 Chips if Odd";
    }
}
