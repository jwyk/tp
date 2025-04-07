package javatro.core.jokers;

import javatro.core.JavatroException;
import javatro.storage.DataParser;
import javatro.storage.StorageManager;
import javatro.storage.utils.CardUtils;
import javatro.storage.Storage;

import java.util.ArrayList;

// @@author jwyk

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
        if (heldJokers.size() >= HeldJokers.HOLDING_LIMIT) {  // Changed to >= for safety
            throw JavatroException.exceedsMaxJokers();
        }
        heldJokers.add(joker);

        Storage storage = Storage.getStorageInstance();
        int runIndex = storage.getRunChosen() - 1;

        // Retrieve current run data
        ArrayList<String> runData = StorageManager.getInstance().getRunData(runIndex);
        assert runData != null : "Run data should not be null";
        assert runData.size() > DataParser.JOKER_HAND_START_INDEX + HeldJokers.HOLDING_LIMIT - 1
                : "Run data is incomplete or corrupted";

        // Update Joker entries in one go
        for (int j = 0; j < HeldJokers.HOLDING_LIMIT; j++) {
            if (heldJokers.isEmpty() || j >= heldJokers.size()) {
                runData.set(DataParser.JOKER_HAND_START_INDEX + j, "-");
            } else {
                runData.set(DataParser.JOKER_HAND_START_INDEX + j,
                        CardUtils.jokerToString(heldJokers.get(j)));
            }
        }

        // Save updated data back to storage manager
        StorageManager.getInstance().saveRunData(runIndex, runData);

        // Persist changes to the save file
        try {
            storage.updateSaveFile();
        } catch (JavatroException e) {
            System.out.println("Failed to save Jokers to file.");
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
