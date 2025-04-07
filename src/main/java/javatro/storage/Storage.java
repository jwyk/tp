package javatro.storage;

import javatro.core.*;

import java.util.*;

public class Storage {
    /** Path to the task storage file. */
    private static Storage storageInstance;

    private static final StorageManager storageManager = StorageManager.getInstance();
    public static Boolean isNewDeck = false;
    private static int runChosen = 0;

    private Storage() {
        try {
            initaliseSaveFile();
        } catch (JavatroException e) {
            System.out.println("Could not initalise task file");
        }
    }

    public static Storage getStorageInstance() {
        if (storageInstance == null) {
            return storageInstance = new Storage();
        }
        return storageInstance;
    }

    public void updateSaveFile() throws JavatroException {
        storageManager.updateSaveFile();
    }

    private void initaliseSaveFile() throws JavatroException {
        storageManager.initaliseSaveFile();
    }

    public TreeMap<Integer, List<String>> getSerializedRunData() {
        TreeMap<Integer, List<String>> copy = new TreeMap<>();
        for (Map.Entry<Integer, ArrayList<String>> entry :
                StorageManager.getInstance().getAllRunData().entrySet()) {
            // Deep copy each list
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    public void setSerializedRunData(TreeMap<Integer, ArrayList<String>> serializedRunData) {
        StorageManager.getInstance().setSerializedRunData(serializedRunData);
    }

    public void addNewRun() {
        // Initialize a new list with default values
        ArrayList<String> newRun = new ArrayList<>();
        isNewDeck = true;

        // Get the new run number
        int arrSize = storageManager.getNumberOfRuns();
        //        int arrSize = serializedRunData.isEmpty() ? 0 : serializedRunData.size();

        // Adding default values as specified
        newRun.add(String.valueOf(arrSize)); // RUN_NUMBER
        newRun.add("1"); // ROUND_NUMBER
        newRun.add("0"); // ROUND_SCORE
        newRun.add("4"); // HAND
        newRun.add("3"); // DISCARD
        newRun.add("1"); // ANTE_NUMBER
        newRun.add("SMALL BLIND"); // BLIND
        newRun.add("0"); // WINS
        newRun.add("0"); // LOSSES
        newRun.add(""); // DECK

        // Add holding hands (8 slots) with "-"
        for (int i = 0; i < 8; i++) {
            newRun.add("-"); // Adding "-"
        }

        // Add jokers (5 slots) with "-"
        for (int i = 0; i < 5; i++) {
            newRun.add("-"); // Adding "-"
        }

        // Add planet card levels (13 slots) with "1"
        for (int i = 0; i < 13; i++) {
            newRun.add("1"); // Adding "1" for each planet card level
        }

        // Add the rest of the deck cards
        for (int i = 0; i < 44; i++) {
            newRun.add("-"); // Adding "-" for rest of the deck cards
        }

        // Add the run to the serializedRunData map using the next run number as the key
        storageManager.saveRunData(arrSize, newRun);
        // serializedRunData.put(arrSize, newRun);

        // Set run chosen to new run
        runChosen = storageManager.getNumberOfRuns();
        // runChosen = serializedRunData.size();
    }

    public int getNumberOfRuns() {
        return storageManager.getNumberOfRuns();
    }

    public String getValue(int runNumber, int idx) {
        ArrayList<String> runData = storageManager.getRunData(runNumber);
        return runData.get(idx);
    }

    public void setValue(int runNumber, int idx, String value) {
        ArrayList<String> runData = storageManager.getRunData(runNumber);
        runData.set(idx, value);
        storageManager.saveRunData(runNumber, runData);
    }

    public int getRunChosen() {
        return runChosen;
    }

    public void setRunChosen(int runChosen) {
        Storage.runChosen = runChosen;
    }
}
