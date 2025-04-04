package javatro.storage;

import javatro.core.JavatroException;
import javatro.display.ansi.DeckArt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static javatro.display.ansi.DeckArt.*;

public class Storage {
    /** Path to the task storage file. */
    private static final String SAVEFILE_LOCATION = "./savefile.csv";
    private static final Path saveFilePath = Paths.get(SAVEFILE_LOCATION);

    private static boolean saveFileValid = true;

    private static Storage storageInstance;



    //Serialized Storage Information stored in a TreeMap
    //[Run Number] [Round Number] [Best Hand] [Ante Number] [Chips] [Wins] [Losses] [Last Action] [Deck] [Level?]
    private static TreeMap<Integer, List<String>> serializedRunData = new TreeMap<>();

    private String csvRawData; //Raw data from csv

    private Storage() throws JavatroException {
        initaliseTaskFile();
    }

    public static Storage getStorageInstance() {
        if(storageInstance == null) {
            try {
                return storageInstance = new Storage();
            } catch (JavatroException e) {
                throw new RuntimeException(e);
            }
        }
        return storageInstance;
    }

    private void createSaveFile() throws JavatroException {
        try {
            // Create the file if it doesn't exist
            Files.createFile(saveFilePath);
        } catch (IOException e) {
            saveFileValid = false;
            throw new JavatroException(
                    "Save File could not be created, current session will not have saving"
                            + " features.");
        }
    }

    private boolean isCSVDataValid() {
        return true;
    }

    private void loadCSVData() {
        String[] runs = csvRawData.split("\\r?\\n");
        System.out.println(runs.length);

        for(int i = 0;i<runs.length;i++) {
            String[] runInfo = runs[i].split(",");
            List<String> runInfoList = Arrays.stream(runInfo).toList();
            System.out.println(runInfoList.get(7));
            serializedRunData.put(i, runInfoList);
        }
    }

    private byte[] convertSerializedDataIntoString() {
        StringBuilder saveData = new StringBuilder();
        for(Integer key: serializedRunData.keySet()) {
            saveData.append(key.toString()).append(",");
            List<String> runInfo = serializedRunData.get(key);
            for(String runAttribute : runInfo) {
                saveData.append(runAttribute).append(",");
            }
            saveData.deleteCharAt(saveData.length()-1); //Removing the last ,
            saveData.append("\n");
        }
        saveData.deleteCharAt(saveData.length()-1); //Removing the last \n
        return saveData.toString().getBytes();
    }

    private void updateSaveFile() throws JavatroException {
        try {
            Files.write(
                    saveFilePath,
                    convertSerializedDataIntoString(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new JavatroException("SAVING ISSUE: " + e.getMessage());
        }
    }

    private void initaliseTaskFile() throws JavatroException {
        // Check if the file exists
        if (Files.exists(saveFilePath)) {
            try {
                //Read the data present in the saveFile.csv
                csvRawData = String.join("\n", Files.readAllLines(saveFilePath));

                //Do validation of the data to ensure that data is valid
                if(!isCSVDataValid()) {
                    //Delete invalid csv file and create new one
                    Files.deleteIfExists(saveFilePath);
                    throw new Exception("Save File Corrupted");
                }

                //Load the data into the treemap
                loadCSVData();

            } catch (Exception e) {
                createSaveFile(); //Create a new save file since current save file is corrupted or could not be read
                throw new JavatroException("Error reading save file: " + e.getMessage());
            }
        } else {
            createSaveFile();
        }
    }

    public static TreeMap<Integer, List<String>> getSerializedRunData() {
        TreeMap<Integer, List<String>> copy = new TreeMap<>();
        for (Map.Entry<Integer, List<String>> entry : serializedRunData.entrySet()) {
            // Deep copy each list
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    public static void setSerializedRunData(TreeMap<Integer, List<String>> serializedRunData) {
        Storage.serializedRunData = serializedRunData;
    }

    public int getNumberOfRuns() {return 5;}

    public String getValue(int runNumber,int idx) {
        return serializedRunData.get(runNumber).get(idx);
    }

    public static DeckArt fromStorageKey(String key) {
        return switch (key.toUpperCase()) {
            case "RED" -> RED_DECK;
            case "BLUE" -> BLUE_DECK;
            case "CHECKERED" -> CHECKERED_DECK;
            case "ABANDONED" -> DeckArt.ABANDONED_DECK;
            default -> throw new IllegalArgumentException("Unknown deck type: " + key);
        };
    }


}
