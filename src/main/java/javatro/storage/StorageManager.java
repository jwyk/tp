package javatro.storage;

import javatro.core.JavatroException;
import javatro.storage.utils.HashUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * The {@code StorageManager} class is responsible for managing the storage of game data, including
 * reading from and writing to a save file. This class handles the serialization and deserialization
 * of game data.
 *
 * <p>This class follows the Singleton pattern to ensure only one instance of the manager exists
 * throughout the application.
 */
public class StorageManager {

    private static final String SAVEFILE_LOCATION = "./savefile.csv";
    private static final Path saveFilePath = Paths.get(SAVEFILE_LOCATION);
    private static StorageManager instance;
    private TreeMap<Integer, ArrayList<String>> serializedRunData = new TreeMap<>();

    /** Private constructor to prevent instantiation. */
    private StorageManager() {}

    /**
     * Returns the single instance of {@code StorageManager}.
     *
     * @return The singleton instance of {@code StorageManager}.
     */
    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    /**
     * Saves a run's data in the storage map.
     *
     * @param runNumber The run number serving as the key.
     * @param runData The run data to be saved.
     */
    public void saveRunData(int runNumber, ArrayList<String> runData) {
        assert runData != null : "Run data should not be null.";
        assert runNumber >= 0 : "Run number should be non-negative.";
        serializedRunData.put(runNumber, runData);
    }

    /**
     * Retrieves the run data for a given run number.
     *
     * @param runNumber The run number for which data is requested.
     * @return The list of strings representing the run data.
     */
    public ArrayList<String> getRunData(int runNumber) {
        assert runNumber >= 0 : "Run number should be non-negative.";
        ArrayList<String> runData = serializedRunData.get(runNumber);
        assert runData != null : "Retrieved run data should not be null.";
        return runData;
    }

    /**
     * Retrieves all run data stored in memory.
     *
     * @return A {@code TreeMap} containing all serialized run data.
     */
    public TreeMap<Integer, ArrayList<String>> getAllRunData() {
        assert serializedRunData != null : "Serialized run data map should not be null.";
        return serializedRunData;
    }

    /**
     * Deletes the data associated with a specific run number.
     *
     * @param runNumber The run number whose data is to be deleted.
     */
    public void deleteRunData(int runNumber) {
        assert runNumber >= 0 : "Run number should be non-negative.";
        assert serializedRunData.containsKey(runNumber) : "Run number must exist in the data.";
        serializedRunData.remove(runNumber);
    }

    /**
     * Returns the number of runs stored in memory.
     *
     * @return The total number of runs.
     */
    public int getNumberOfRuns() {
        return serializedRunData.size();
    }

    /**
     * Sets the entire serialized run data map.
     *
     * @param serializedRunData The {@code TreeMap} containing serialized run data.
     */
    public void setSerializedRunData(TreeMap<Integer, ArrayList<String>> serializedRunData) {
        assert serializedRunData != null : "Serialized run data map should not be null.";
        this.serializedRunData = serializedRunData;
    }

    /**
     * Creates a new save file if it does not exist.
     *
     * @throws JavatroException if the file cannot be created.
     */
    public void createSaveFile() throws JavatroException {
        try {
            Files.createFile(saveFilePath);
            assert Files.exists(saveFilePath) : "Save file should be created successfully.";
        } catch (IOException e) {
            throw new JavatroException("Save File could not be created.");
        }
    }

    /**
     * Converts all serialized run data into a byte array formatted for saving.
     *
     * @return A byte array representing the serialized run data.
     */
    private byte[] convertSerializedDataIntoString() {
        assert serializedRunData != null : "Serialized run data should not be null.";

        StringBuilder saveData = new StringBuilder();
        for (Integer key : serializedRunData.keySet()) {
            List<String> runInfo = serializedRunData.get(key);
            assert runInfo != null : "Run data list should not be null.";

            List<String> sanitizedRunInfo = new ArrayList<>();

            for (int i = 0; i < DataParser.START_OF_REST_OF_DECK + 44; i++) {
                String runAttribute = runInfo.get(i).trim();

                if (runAttribute.equals("-") || runAttribute.isEmpty()) {
                    runAttribute = "NA";
                }

                sanitizedRunInfo.add(runAttribute);
                saveData.append(runAttribute);

                if (i < DataParser.START_OF_REST_OF_DECK + 44 - 1) {
                    saveData.append(",");
                }
            }

            String hash = HashUtil.generateHash(sanitizedRunInfo);
            saveData.append(",").append(hash).append("\n");
        }

        assert !saveData.isEmpty() : "Serialized data should not be empty.";

        saveData.deleteCharAt(saveData.length() - 1);
        return saveData.toString().getBytes();
    }

    /**
     * Updates the save file by writing the serialized run data to disk.
     *
     * @throws JavatroException if the file cannot be written.
     */
    public void updateSaveFile() throws JavatroException {
        try {
            Files.write(
                    saveFilePath,
                    convertSerializedDataIntoString(),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            assert Files.exists(saveFilePath) : "Save file should exist after updating.";
        } catch (IOException e) {
            throw new JavatroException("Saving issue: " + e.getMessage());
        }
    }

    /**
     * Initializes the save file by loading existing data or creating a new file if none exists.
     *
     * @throws JavatroException if the file cannot be read or created.
     */
    public void initaliseSaveFile() throws JavatroException {
        if (Files.exists(saveFilePath)) {
            try {
                String csvRawData = String.join("\n", Files.readAllLines(saveFilePath));

                if (!csvRawData.trim().isEmpty()) {
                    assert DataParser.isCSVDataValid(csvRawData) : "CSV data should be valid.";
                    DataParser.loadCSVData(csvRawData);
                }
            } catch (Exception e) {
                createSaveFile();
                System.out.println("Creating new save file..");
            }
        } else {
            createSaveFile();
        }
    }

    public void updateRoundData(int runIndex, List<String> roundData) {
        assert roundData != null : "Round data cannot be null";
        assert runIndex >= 0 : "Run index cannot be negative";

        ArrayList<String> existingData = getRunData(runIndex);

        if (existingData != null) {
            // Update relevant indexes based on DataParser constants
            existingData.set(DataParser.ROUND_NUMBER_INDEX, roundData.get(0));
            existingData.set(DataParser.ANTE_NUMBER_INDEX, roundData.get(1));
            existingData.set(DataParser.BLIND_INDEX, roundData.get(2));

            // Update holding hands (assuming holding hands come next in the list)
            for (int i = 0; i < 8; i++) {
                existingData.set(DataParser.HOLDING_HAND_START_INDEX + i, roundData.get(3 + i));
            }

            // Save back to storage
            saveRunData(runIndex, existingData);
        } else {
            throw new IllegalArgumentException(
                    "Run data for index " + runIndex + " does not exist.");
        }
    }

    /**
     * Updates round-specific data for a new round in the storage.
     *
     * @param runIndex The index of the current run being updated.
     * @param roundData A list containing the updated round data in the following order: - Remaining
     *     plays (HAND_INDEX) - Remaining discards (DISCARD_INDEX) - Current round score
     *     (ROUND_SCORE_INDEX)
     */
    public void updateNewRoundData(int runIndex, List<String> roundData) {
        assert roundData.size() == 3 : "Round data must contain exactly 3 elements";
        assert runIndex >= 0 : "Run index cannot be negative";

        ArrayList<String> existingData = getRunData(runIndex);
        if (existingData != null) {
            // Update relevant indices
            existingData.set(DataParser.HAND_INDEX, roundData.get(0));
            existingData.set(DataParser.DISCARD_INDEX, roundData.get(1));
            existingData.set(DataParser.ROUND_SCORE_INDEX, roundData.get(2));

            // Save updated data to storage
            saveRunData(runIndex, existingData);
        } else {
            throw new IllegalArgumentException(
                    "Run data for index " + runIndex + " does not exist.");
        }
    }
}
