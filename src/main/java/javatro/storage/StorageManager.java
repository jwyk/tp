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

public class StorageManager {

    private static StorageManager instance;

    private TreeMap<Integer, ArrayList<String>> serializedRunData = new TreeMap<>();
    private static final String SAVEFILE_LOCATION = "./savefile.csv";
    private static final Path saveFilePath = Paths.get(SAVEFILE_LOCATION);
    private String csvRawData; // Raw data from csv

    private StorageManager() {}

    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    public void saveRunData(int runNumber, ArrayList<String> runData) {
        serializedRunData.put(runNumber, runData);
    }

    public ArrayList<String> getRunData(int runNumber) {
        return serializedRunData.get(runNumber);
    }

    public TreeMap<Integer, ArrayList<String>> getAllRunData() {
        return serializedRunData;
    }

    public void deleteRunData(int runNumber) {
        serializedRunData.remove(runNumber);
    }

    public int getNumberOfRuns() {
        return serializedRunData.size();
    }

    public void setSerializedRunData(TreeMap<Integer, ArrayList<String>> serializedRunData) {
        this.serializedRunData = serializedRunData;
    }

    public void createSaveFile() throws JavatroException {
        try {
            // Create the file if it doesn't exist
            Files.createFile(saveFilePath);
        } catch (IOException e) {
            throw new JavatroException(
                    "Save File could not be created, current session will not have saving"
                            + " features.");
        }
    }

    private byte[] convertSerializedDataIntoString() {
        StringBuilder saveData = new StringBuilder();
        for (Integer key : StorageManager.getInstance().getAllRunData().keySet()) {
            List<String> runInfo = StorageManager.getInstance().getAllRunData().get(key);

            List<String> sanitizedRunInfo = new ArrayList<>();

            for (int i = 0; i < DataParser.START_OF_REST_OF_DECK + 44; i++) {
                String runAttribute = runInfo.get(i).trim();

                // Normalize empty or placeholder entries
                if (runAttribute.equals("-") || runAttribute.isEmpty()) {
                    runAttribute = "NA";
                }

                sanitizedRunInfo.add(runAttribute);
                saveData.append(runAttribute);

                if (i < DataParser.START_OF_REST_OF_DECK + 44 - 1) { // Avoid trailing comma
                    saveData.append(",");
                }
            }

            // Generate a hash for the row using sanitizedRunInfo
            String hash = HashUtil.generateHash(sanitizedRunInfo);
            saveData.append(",").append(hash).append("\n");
        }

        saveData.deleteCharAt(saveData.length() - 1); // Removing the last \n
        return saveData.toString().getBytes();
    }

    public void updateSaveFile() throws JavatroException {
        try {
            Files.write(
                    saveFilePath,
                    convertSerializedDataIntoString(),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new JavatroException("SAVING ISSUE: " + e.getMessage());
        }
    }

    public void initaliseSaveFile() throws JavatroException {
        // Check if the file exists
        if (Files.exists(saveFilePath)) {
            try {
                // Read the data present in the saveFile.csv
                csvRawData = String.join("\n", Files.readAllLines(saveFilePath));

                if (csvRawData.trim().isEmpty()) { // Check for null or empty string
                    return;
                }

                // Do validation of the data to ensure that data is valid
                if (!DataParser.isCSVDataValid(csvRawData)) {
                    // Delete invalid csv file and create new one
                    Files.deleteIfExists(saveFilePath);
                    throw new Exception("Save File Corrupted");
                }

                // Load the data into the treemap
                DataParser.loadCSVData(csvRawData);

            } catch (Exception e) {
                createSaveFile(); // Create a new save file since current save file is corrupted or
                                  // has not been created
                System.out.println("Creating new save file..");
            }
        } else {
            createSaveFile();
        }
    }
}
