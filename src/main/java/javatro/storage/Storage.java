package javatro.storage;

import javatro.core.JavatroException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The {@code Storage} class serves as a facade to interact with the underlying
 * storage mechanism managed by {@link StorageManager}. It provides methods for
 * saving, loading, and updating game data runs. This class uses the Singleton pattern.
 */
public class Storage {

    /** Singleton instance of Storage. */
    private static Storage storageInstance;

    /** Singleton instance of StorageManager responsible for handling storage operations. */
    private static final StorageManager storageManager = StorageManager.getInstance();

    /** Indicates if a new deck is being used. */
    public static Boolean isNewDeck = false;

    /** Stores the index of the current run. */
    private static int runChosen = 0;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the save file by calling the underlying {@link StorageManager}.
     */
    private Storage() {
        try {
            initaliseSaveFile();
        } catch (JavatroException e) {
            System.out.println("Could not initialize task file");
        }
    }

    /**
     * Retrieves the singleton instance of {@code Storage}.
     *
     * @return The singleton instance of {@code Storage}.
     */
    public static Storage getStorageInstance() {
        if (storageInstance == null) {
            return storageInstance = new Storage();
        }
        return storageInstance;
    }

    /**
     * Updates the save file by delegating to the {@link StorageManager}.
     *
     * @throws JavatroException If an error occurs during saving.
     */
    public void updateSaveFile() throws JavatroException {
        storageManager.updateSaveFile();
    }

    /**
     * Initializes the save file by calling {@link StorageManager#initaliseSaveFile()}.
     *
     * @throws JavatroException If an error occurs during initialization.
     */
    private void initaliseSaveFile() throws JavatroException {
        storageManager.initaliseSaveFile();
    }

    /**
     * Retrieves a deep copy of the serialized run data.
     *
     * @return A {@code TreeMap} containing all serialized run data.
     */
    public TreeMap<Integer, List<String>> getSerializedRunData() {
        TreeMap<Integer, List<String>> copy = new TreeMap<>();
        for (Map.Entry<Integer, ArrayList<String>> entry : storageManager.getAllRunData().entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    /**
     * Sets the serialized run data by delegating to the {@link StorageManager}.
     *
     * @param serializedRunData The run data to be set.
     */
    public void setSerializedRunData(TreeMap<Integer, ArrayList<String>> serializedRunData) {
        storageManager.setSerializedRunData(serializedRunData);
    }

    /**
     * Adds a new run to the storage with default values.
     */
    public void addNewRun() {
        ArrayList<String> newRun = new ArrayList<>();
        isNewDeck = true;

        int arrSize = storageManager.getNumberOfRuns();

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

        for (int i = 0; i < 8; i++) newRun.add("-");
        for (int i = 0; i < 5; i++) newRun.add("-");
        for (int i = 0; i < 13; i++) newRun.add("1");
        for (int i = 0; i < 44; i++) newRun.add("-");

        storageManager.saveRunData(arrSize, newRun);
        runChosen = storageManager.getNumberOfRuns();
    }

    /**
     * Retrieves the total number of runs stored.
     *
     * @return The total number of runs.
     */
    public int getNumberOfRuns() {
        return storageManager.getNumberOfRuns();
    }

    /**
     * Retrieves a specific value from a run.
     *
     * @param runNumber The run number.
     * @param idx The index of the value to retrieve.
     * @return The value as a {@code String}.
     */
    public String getValue(int runNumber, int idx) {
        ArrayList<String> runData = storageManager.getRunData(runNumber);
        return runData.get(idx);
    }

    /**
     * Sets a specific value for a given run and saves it.
     *
     * @param runNumber The run number.
     * @param idx The index of the value to set.
     * @param value The value to be set.
     */
    public void setValue(int runNumber, int idx, String value) {
        ArrayList<String> runData = storageManager.getRunData(runNumber);
        runData.set(idx, value);
        storageManager.saveRunData(runNumber, runData);
    }

    /**
     * Retrieves the currently selected run index.
     *
     * @return The index of the current run.
     */
    public int getRunChosen() {
        return runChosen;
    }

    /**
     * Sets the currently selected run index.
     *
     * @param runChosen The run index to set.
     */
    public void setRunChosen(int runChosen) {
        Storage.runChosen = runChosen;
    }

}
