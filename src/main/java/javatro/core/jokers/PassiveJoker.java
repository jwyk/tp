package javatro.core.jokers;

import javatro.core.Card;
import javatro.core.JavatroCore;
import javatro.core.Score;


/**
 * Represents a Passive Type Joker card. Applies effect on Round instantiation.
 */

public abstract class PassiveJoker extends Joker {

    /**
     * Abstract method of Joker to trigger when it is deleted. To be called when a passive bonus should be removed from
     * a {@JavatroCore} parameter.
     * Overriden by Joker subclasses.
     * @param javatroCore JavatroCore class to extract parameters from.
     */
    public abstract void delete(JavatroCore javatroCore);
}
