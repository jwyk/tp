package javatro.core.jokers.passive;

import javatro.core.Card;
import javatro.core.JavatroCore;
import javatro.core.Score;
import javatro.core.jokers.PassiveJoker;

public class DrunkardJoker extends PassiveJoker {

    public DrunkardJoker() {
        super();
        this.description = "+1 Discards per Round ";
        this.scoreType = ScoreType.PASSIVE;
        JavatroCore.totalPlays += 1;
    }

    @Override
    public void interact(Score scoreClass, Card playedCard) {}

    @Override
    public void delete(JavatroCore javatroCore) {}

    @Override
    public String toString() {
        return "+1 Discard";
    }
}
