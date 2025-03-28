package javatro.core.jokers;

import java.util.ArrayList;

import javatro.core.Card;
import javatro.core.JavatroException;
import javatro.core.Score;


/**
 * Holds all the Jokers the player has in an ArrayList of type Joker.
 */

public class HeldJokers {
    private static int HOLDING_LIMIT;
    public ArrayList<Joker> heldJokers;

    /** Constructor for the HeldJokers Class. */
    public HeldJokers() {
        heldJokers = new ArrayList<Joker>(5);
        //By Default, HOLDING_LIMIT is 5.
        HOLDING_LIMIT = 5;
    }

    /** Adds 1 Joker to the HeldJokers Class. */
    public void add(Joker joker) throws JavatroException {
        if (heldJokers.size() > 5) {
            throw JavatroException.exceedsMaxJokers();
        }
        heldJokers.add(joker);
    }

    /**
     * Returns a soft copy of the ArrayList of HeldJokers.
     */
    public ArrayList<Joker> getJokers() {
        return new ArrayList<Joker>(heldJokers);
    }

}
