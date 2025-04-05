package javatro.storage;

import static javatro.core.Ante.Blind.*;
import static javatro.display.ansi.DeckArt.ABANDONED_DECK;
import static javatro.display.ansi.DeckArt.BLUE_DECK;
import static javatro.display.ansi.DeckArt.CHECKERED_DECK;
import static javatro.display.ansi.DeckArt.RED_DECK;

import javatro.core.Ante;
import javatro.core.Deck;
import javatro.core.JavatroException;
import javatro.display.ansi.DeckArt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Storage {
    /** Path to the task storage file. */
    private static final String SAVEFILE_LOCATION = "./savefile.csv";

    private static final Path saveFilePath = Paths.get(SAVEFILE_LOCATION);

    private static boolean saveFileValid = true;
    private static int runChosen = 0;

    private static final int EXPECTED_COLUMNS = 13;
    private static final Set<String> VALID_DECKS = Set.of("RED", "ABANDONED", "CHECKERED", "BLUE");
    private static final Set<String> VALID_BLINDS = Set.of("SMALL BLIND", "LARGE BLIND", "BOSS BLIND");


    // Basic Info Indexes (Fixed Position)
    public static final int RUN_NUMBER_INDEX = 0;
    public static final int ROUND_NUMBER_INDEX = 1;
    public static final int ROUND_SCORE_INDEX = 2;
    public static final int HAND_INDEX = 3;
    public static final int DISCARD_INDEX = 4;
    public static final int ANTE_NUMBER_INDEX = 5;
    public static final int BLIND_INDEX = 6;
    public static final int WINS_INDEX = 7;
    public static final int LOSSES_INDEX = 8;
    public static final int DECK_INDEX = 9;

    // Dynamic Data Indexes (Varying Size)
    public static final int HOLDING_HAND_START_INDEX = 10; // Maximum 8 holding hands (10 to 19)
    public static final int JOKER_HAND_START_INDEX = 20;   // Maximum 5 Joker Hands (20 to 24)
    public static final int PLANET_CARD_START_INDEX = 25;  // Unlimited Planet Cards (25 onwards)


    private static Storage storageInstance;

    // Serialized Storage Information stored in a TreeMap
    private static TreeMap<Integer, ArrayList<String>> serializedRunData = new TreeMap<>();

    private String csvRawData; // Raw data from csv

    private Storage() throws JavatroException {
        initaliseTaskFile();
    }

    public static Storage getStorageInstance() {
        if (storageInstance == null) {
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

    public boolean isCSVDataValid() {

        String[] rows = csvRawData.split("\\r?\\n"); // Split by newline, handling Windows and Unix line endings

        for (String row : rows) {
            row = row.trim();

            if (row.isEmpty()) continue; // Skip empty lines

            String[] columns = row.split(",");

            // Check if the row has at least enough columns for predefined indexes
            if (columns.length < DECK_INDEX + 1) { // +1 because array index is zero-based
                System.out.println("Invalid number of columns in row: " + row);
                return false;
            }

            // Check if deck name is valid
            String deckName = columns[DECK_INDEX].trim();
            if (!VALID_DECKS.contains(deckName)) {
                System.out.println("Invalid deck name in row: " + row);
                return false;
            }

            // Check if blind name is valid
            String blindName = columns[BLIND_INDEX].trim();
            if (!VALID_BLINDS.contains(blindName)) {
                System.out.println("Invalid blind name in row: " + row);
                return false;
            }


            // Validate predefined numeric columns
            try {
                int[] numericIndexes = {
                        RUN_NUMBER_INDEX, ROUND_NUMBER_INDEX, ROUND_SCORE_INDEX, HAND_INDEX, DISCARD_INDEX,
                        ANTE_NUMBER_INDEX,  WINS_INDEX, LOSSES_INDEX
                };

                for (int index : numericIndexes) {
                    Integer.parseInt(columns[index]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric value in row: " + row);
                return false;
            }
        }

        return true; // All rows are valid
    }

    private void loadCSVData() {
        String[] runs = csvRawData.split("\\r?\\n");
        System.out.println(runs.length);

        for (int i = 0; i < runs.length; i++) {
            String[] runInfo = runs[i].split(",");
            ArrayList<String> runInfoList = new ArrayList<>(Arrays.asList(runInfo));
            System.out.println(runInfoList.get(7));
            serializedRunData.put(i, runInfoList);
        }
    }

    private byte[] convertSerializedDataIntoString() {
        StringBuilder saveData = new StringBuilder();
        for (Integer key : serializedRunData.keySet()) {
            List<String> runInfo = serializedRunData.get(key);
            for (String runAttribute : runInfo) {
                saveData.append(runAttribute).append(",");
            }
            saveData.deleteCharAt(saveData.length() - 1); // Removing the last ,
            saveData.append("\n");
        }
        saveData.deleteCharAt(saveData.length() - 1); // Removing the last \n
        return saveData.toString().getBytes();
    }

    public void updateSaveFile() throws JavatroException {
        try {
            Files.write(saveFilePath, convertSerializedDataIntoString(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new JavatroException("SAVING ISSUE: " + e.getMessage());
        }
    }

    private void initaliseTaskFile() throws JavatroException {
        // Check if the file exists
        if (Files.exists(saveFilePath)) {
            try {
                // Read the data present in the saveFile.csv
                csvRawData = String.join("\n", Files.readAllLines(saveFilePath));

                if (csvRawData.trim().isEmpty()) { // Check for null or empty string
                    return;
                }

                // Do validation of the data to ensure that data is valid
                if (!isCSVDataValid()) {
                    // Delete invalid csv file and create new one
                    Files.deleteIfExists(saveFilePath);
                    throw new Exception("Save File Corrupted");
                }

                // Load the data into the treemap
                loadCSVData();

            } catch (Exception e) {
                createSaveFile(); // Create a new save file since current save file is corrupted or
                                  // could not be read
                System.out.println("Creating new save file..");
            }
        } else {
            createSaveFile();
        }
    }

    public TreeMap<Integer, List<String>> getSerializedRunData() {
        TreeMap<Integer, List<String>> copy = new TreeMap<>();
        for (Map.Entry<Integer, ArrayList<String>> entry : serializedRunData.entrySet()) {
            // Deep copy each list
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    public void setSerializedRunData(TreeMap<Integer, ArrayList<String>> serializedRunData) {
        Storage.serializedRunData = serializedRunData;
    }

    public void addNewRun() {
        // Initialize an empty list with nulls of size EXPECTED_COLUMNS
        ArrayList<String> newRun = new ArrayList<>();
        int arrSize = serializedRunData.isEmpty() ? 1 : serializedRunData.size();

        //3,1,320,2,150,4,5,SORT,BLUE, BOSS BLIND
        // [Run Number] [Round Number] [Best Hand] [Ante Number] [Chips] [Wins] [Losses] [Last Action]
        // Add placeholders for all columns
        newRun.add(String.valueOf(arrSize));
        newRun.add("1");
        for (int i = 2; i <= 6; i++) {
            newRun.add("0");  // You can change this to "0" or "N/A" if you prefer
        }
        for (int i = 7; i < EXPECTED_COLUMNS; i++) {
            newRun.add("NA");  // You can change this to "0" or "N/A" if you prefer
        }

        // Add the run to the serializedRunData map using the next run number as the key
        serializedRunData.put(arrSize, newRun);


        // Set run chosen to new run
        runChosen = serializedRunData.size()-1;

    }

    public int getNumberOfRuns() {return serializedRunData.size();}

    public String getValue(int runNumber, int idx) {
        return serializedRunData.get(runNumber).get(idx);
    }

    public void setValue(int runNumber, int idx, String value) {
        serializedRunData.get(runNumber).set(idx,value);
    }


    public int getRunChosen() {
        return runChosen;
    }

    public void setRunChosen(int runChosen) {
        Storage.runChosen = runChosen;
    }


    public static DeckArt fromStorageKey(String key) {
        return switch (key.toUpperCase()) {
            case "RED" -> RED_DECK;
            case "BLUE" -> BLUE_DECK;
            case "CHECKERED" -> CHECKERED_DECK;
            case "ABANDONED" -> ABANDONED_DECK;
            default -> throw new IllegalArgumentException("Unknown deck art: " + key);
        };
    }

    public static Deck.DeckType DeckFromKey(String key) {
        return switch (key.toUpperCase()) {
            case "RED" -> Deck.DeckType.RED;
            case "BLUE" -> Deck.DeckType.BLUE;
            case "CHECKERED" -> Deck.DeckType.CHECKERED;
            case "ABANDONED" -> Deck.DeckType.ABANDONED;
            default -> throw new IllegalArgumentException("Unknown deck type: " + key);
        };
    }

    public static Ante.Blind BlindFromKey(String key) {
        return switch (key.toUpperCase()) {
            case "SMALL BLIND" -> SMALL_BLIND;
            case "LARGE BLIND" -> LARGE_BLIND;
            case "BOSS BLIND" -> BOSS_BLIND;
            default -> throw new IllegalArgumentException("Unknown blind type: " + key);
        };
    }

}
