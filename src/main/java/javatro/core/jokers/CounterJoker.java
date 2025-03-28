package javatro.core.jokers;


import javatro.core.Card;
import javatro.core.Score;

/**
 * Represents a CounterJoker Joker, based off the Abstract Joker of Balatro.
 */

public class CounterJoker extends Joker {
    private int numberOfJokers;

    public CounterJoker(int numberOfJokers) {
        super();
        this.description = "+3 Mult for each Joker card held." +
                "Current Multiplier: " + numberOfJokers;
        this.numberOfJokers = numberOfJokers;
        this.scoreType = ScoreType.AFTERPLAYHAND;
    }

    @Override
    public void interact (Score scoreClass, Card playedCard) {
        numberOfJokers = scoreClass.jokerList.size();
        this.description = "+3 Mult for each Joker card held." +
                "Current Multiplier: " + numberOfJokers;
        scoreClass.totalMultiplier += numberOfJokers * 3;
    }

    @Override
    public String toString() {
        return "+3 Mult Spades";
    }

}
