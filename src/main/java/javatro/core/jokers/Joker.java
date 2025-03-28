package javatro.core.jokers;

import javatro.core.Card;
import javatro.core.Score;

/**
 * Represents a Joker card. Each Joker card has a description and a string representation of the Joker Object.
 */

public abstract class Joker {
    protected String description;

    /**
     * Abstract method of Joker for scoring purposes. Overriden by Joker subclasses.
     */
    public abstract void interact (Score scoreClass, Card playedCard);
}
