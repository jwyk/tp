package javatro.core.jokers;

import java.util.ArrayList;

import javatro.core.JavatroException;


/**
 * Holds all the Jokers the player has
 * Contains an ArrayList of type Joker.
 */

public class HeldJokers {
    private static int HOLDING_LIMIT;
    public ArrayList<Joker> heldJokers;

    /**
     * Constructor for the HeldJokers Class.
     */
    public HeldJokers() {
        heldJokers = new ArrayList<Joker>(5);
        HOLDING_LIMIT = 5;
    }

    /**
     * Adds 1 Joker to the HeldJokers Class.
     */
    public void add(Joker joker) throws JavatroException {
        if (heldJokers.size() > 5) {
            throw JavatroException.exceedsMaxJokers();
        }
        heldJokers.add(joker);
    }

    /**
     * Returns the ArrayList of Jokers.
     */
    public ArrayList<Joker> getJokers() {
        return heldJokers;
    }

}
