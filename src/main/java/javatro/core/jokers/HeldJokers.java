package javatro.core.jokers;

import static javatro.core.JavatroCore.heldJokers;

import javatro.core.JavatroException;
import javatro.storage.Storage;

import java.util.ArrayList;

/** Holds all the Jokers the player has in an ArrayList of type Joker. */
public class HeldJokers {
    // By Default, HOLDING_LIMIT is 5.
    public static final int HOLDING_LIMIT = 5;
    public ArrayList<Joker> heldJokers;

    /** Constructor for the HeldJokers Class. */
    public HeldJokers() {
        heldJokers = new ArrayList<Joker>(5);
    }

    /** Adds 1 Joker to the HeldJokers Class. */
    public void add(Joker joker) throws JavatroException {
        if (heldJokers.size() > 5) {
            throw JavatroException.exceedsMaxJokers();
        }
        heldJokers.add(joker);

        if (Storage.saveActive) {
            Storage storage = Storage.getStorageInstance();
            // Update Joker Cards
            for (int j = 0; j < HeldJokers.HOLDING_LIMIT; j++) {
                if (heldJokers.isEmpty() || j >= heldJokers.size()) {
                    storage.setValue(
                            storage.getRunChosen() - 1, Storage.JOKER_HAND_START_INDEX + j, "-");
                } else {
                    storage.setValue(
                            storage.getRunChosen() - 1,
                            Storage.JOKER_HAND_START_INDEX + j,
                            Storage.jokerToString(heldJokers.get(j)));
                }
            }
        }
    }

    /** Removes the Joker from the specified index. */
    public void remove(int index) throws JavatroException {
        if (index < 0 || index >= heldJokers.size()) {
            throw JavatroException.indexOutOfBounds(index);
        }
        heldJokers.remove(index);
    }

    /** Returns a soft copy of the ArrayList of HeldJokers. */
    public ArrayList<Joker> getJokers() {
        return heldJokers;
    }
}
