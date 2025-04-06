package javatro.storage;

import static javatro.core.Ante.Blind.*;
import static javatro.display.ansi.DeckArt.ABANDONED_DECK;
import static javatro.display.ansi.DeckArt.BLUE_DECK;
import static javatro.display.ansi.DeckArt.CHECKERED_DECK;
import static javatro.display.ansi.DeckArt.RED_DECK;

import javatro.core.*;
import javatro.core.jokers.Joker;
import javatro.core.jokers.addchip.OddToddJoker;
import javatro.core.jokers.addchip.ScaryFaceJoker;
import javatro.core.jokers.addmult.*;
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
    private static final Set<String> VALID_BLINDS =
            Set.of("SMALL BLIND", "LARGE BLIND", "BOSS BLIND");

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
    public static final int HOLDING_HAND_START_INDEX = 10; // Maximum 8 holding hands (10 to 17)
    public static final int JOKER_HAND_START_INDEX = 18; // Maximum 5 Joker Hands (18 to 22)

    // Planet Card Level Indexes
    public static final int HIGH_CARD_INDEX = 23;
    public static final int PAIR_INDEX = 24;
    public static final int TWO_PAIR_INDEX = 25;
    public static final int THREE_OF_A_KIND_INDEX = 26;
    public static final int STRAIGHT_INDEX = 27;
    public static final int FLUSH_INDEX = 28;
    public static final int FULL_HOUSE_INDEX = 29;
    public static final int FOUR_OF_A_KIND_INDEX = 30;
    public static final int STRAIGHT_FLUSH_INDEX = 31;
    public static final int ROYAL_FLUSH_INDEX = 32;
    public static final int FIVE_OF_A_KIND_INDEX = 33;
    public static final int FLUSH_HOUSE_INDEX = 34;
    public static final int FLUSH_FIVE_INDEX = 35;
    public static final int START_OF_REST_OF_DECK = 36;

    private static Storage storageInstance;

    // Serialized Storage Information stored in a TreeMap
    private static TreeMap<Integer, ArrayList<String>> serializedRunData = new TreeMap<>();

    private String csvRawData; // Raw data from csv

    public static Boolean isNewDeck = false;

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

        String[] rows =
                csvRawData.split(
                        "\\r?\\n"); // Split by newline, handling Windows and Unix line endings

        for (String row : rows) {
            row = row.trim();

            if (row.isEmpty()) continue; // Skip empty lines

            String[] columns = row.split(",");

            // Check if the row has at least enough columns for predefined indexes and planet cards
            if (columns.length
                    < Storage.HIGH_CARD_INDEX
                            + 13) { // Adjusting for all planet card levels (13 total)
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
                    RUN_NUMBER_INDEX,
                    ROUND_NUMBER_INDEX,
                    ROUND_SCORE_INDEX,
                    HAND_INDEX,
                    DISCARD_INDEX,
                    WINS_INDEX,
                    LOSSES_INDEX
                };

                for (int index : numericIndexes) {
                    Integer.parseInt(columns[index]);
                }

                // Validate Ante Number (must be between 1 and 8)
                int anteNumber = Integer.parseInt(columns[ANTE_NUMBER_INDEX]);
                if (anteNumber < 1 || anteNumber > 8) {
                    System.out.println(
                            "Invalid Ante Number (must be between 1 and 8): " + anteNumber);
                    return false;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric value in row: " + row);
                return false;
            }

            // Validate holding hands (Fixed 8 slots)
            for (int i = HOLDING_HAND_START_INDEX; i < JOKER_HAND_START_INDEX; i++) {
                String card = columns[i].trim();
                if (!card.equals("-") && !isValidCardString(card)) {
                    System.out.println("Invalid holding card: " + card);
                    return false;
                }
            }

            // Validate joker hands (Fixed 5 slots)
            for (int i = JOKER_HAND_START_INDEX; i < HIGH_CARD_INDEX; i++) {
                String joker = columns[i].trim();
                if (!joker.equals("-") && !isValidJokerString(joker)) {
                    System.out.println("Invalid joker card: " + joker);
                    return false;
                }
            }

            // Validate planet card levels (From PLANET_CARD_START_INDEX onwards)
            for (int i = HIGH_CARD_INDEX; i < START_OF_REST_OF_DECK; i++) {
                try {
                    int level = Integer.parseInt(columns[i].trim());
                    if (level < 1) { // Assuming levels must be positive integers
                        System.out.println(
                                "Invalid planet card level at index " + i + ": " + columns[i]);
                        return false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Invalid planet card level at index " + i + ": " + columns[i]);
                    return false;
                }
            }

            // Validate the rest of the deck
            for (int i = START_OF_REST_OF_DECK; i < START_OF_REST_OF_DECK + 44; i++) {
                String card = columns[i].trim();
                if (!card.equals("-") && !isValidCardString(card)) {
                    System.out.println("Invalid rest of the deck card: " + card);
                    return false;
                }
            }
        }

        return true; // All rows are valid
    }

    private void loadCSVData() {
        String[] runs = csvRawData.split("\\r?\\n");

        for (int i = 0; i < runs.length; i++) {
            String[] runInfo = runs[i].split(",");
            ArrayList<String> runInfoList = new ArrayList<>(Arrays.asList(runInfo));
            serializedRunData.put(i, runInfoList);
        }
    }

    private byte[] convertSerializedDataIntoString() {
        StringBuilder saveData = new StringBuilder();
        for (Integer key : serializedRunData.keySet()) {
            List<String> runInfo = serializedRunData.get(key);
            for (int i = 0; i < Storage.START_OF_REST_OF_DECK + 44; i++) {
                String runAttribute = runInfo.get(i);
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
            Files.write(
                    saveFilePath,
                    convertSerializedDataIntoString(),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
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
        // Initialize a new list with default values
        ArrayList<String> newRun = new ArrayList<>();
        isNewDeck = true;

        // Get the new run number
        int arrSize = serializedRunData.isEmpty() ? 0 : serializedRunData.size();

        // Adding default values as specified
        newRun.add(String.valueOf(arrSize)); // RUN_NUMBER
        newRun.add("1"); // ROUND_NUMBER
        newRun.add("0"); // ROUND_SCORE
        newRun.add("4"); // HAND
        newRun.add("5"); // DISCARD
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
        serializedRunData.put(arrSize, newRun);

        // Set run chosen to new run
        runChosen = serializedRunData.size();
    }

    public int getNumberOfRuns() {
        return serializedRunData.size();
    }

    public String getValue(int runNumber, int idx) {
        return serializedRunData.get(runNumber).get(idx);
    }

    public void setValue(int runNumber, int idx, String value) {
        serializedRunData.get(runNumber).set(idx, value);
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

    public static Card parseCardString(String cardString) {
        // Ensure the string is not null or empty
        if (cardString == null || cardString.length() < 2) {
            throw new IllegalArgumentException("Invalid card string");
        }

        // Extract the rank and suit from the string
        String rankStr =
                cardString.substring(0, cardString.length() - 1); // All but the last character
        char suitChar = cardString.charAt(cardString.length() - 1); // Last character

        // Parse the rank
        Card.Rank rank =
                switch (rankStr) {
                    case "2" -> Card.Rank.TWO;
                    case "3" -> Card.Rank.THREE;
                    case "4" -> Card.Rank.FOUR;
                    case "5" -> Card.Rank.FIVE;
                    case "6" -> Card.Rank.SIX;
                    case "7" -> Card.Rank.SEVEN;
                    case "8" -> Card.Rank.EIGHT;
                    case "9" -> Card.Rank.NINE;
                    case "10" -> Card.Rank.TEN;
                    case "J" -> Card.Rank.JACK;
                    case "Q" -> Card.Rank.QUEEN;
                    case "K" -> Card.Rank.KING;
                    case "A" -> Card.Rank.ACE;
                    default -> throw new IllegalArgumentException("Invalid rank: " + rankStr);
                };

        // Parse the suit
        Card.Suit suit =
                switch (Character.toUpperCase(suitChar)) {
                    case 'H' -> Card.Suit.HEARTS;
                    case 'C' -> Card.Suit.CLUBS;
                    case 'S' -> Card.Suit.SPADES;
                    case 'D' -> Card.Suit.DIAMONDS;
                    default -> throw new IllegalArgumentException("Invalid suit: " + suitChar);
                };

        // Return the constructed Card
        return new Card(rank, suit);
    }

    public static String cardToString(Card card) {
        // Get the rank and suit from the card
        String rankStr = card.rank().getSymbol(); // Get the symbol (e.g., "A", "K", "10")
        String suitStr =
                switch (card.suit()) {
                    case HEARTS -> "H";
                    case CLUBS -> "C";
                    case SPADES -> "S";
                    case DIAMONDS -> "D";
                };

        // Combine rank and suit to form the card string
        return rankStr + suitStr;
    }

    public static boolean isValidCardString(String cardString) {
        if (cardString == null || cardString.length() < 2) {
            return false; // Null or too short to be a valid card
        }

        // Extract the rank and suit from the string
        String rankStr =
                cardString.substring(0, cardString.length() - 1); // All but the last character
        char suitChar = cardString.charAt(cardString.length() - 1); // Last character

        // Check if the rank is valid
        boolean isValidRank =
                switch (rankStr) {
                    case "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" -> true;
                    default -> false;
                };

        // Check if the suit is valid
        boolean isValidSuit =
                switch (Character.toUpperCase(suitChar)) {
                    case 'H', 'C', 'S', 'D' -> true;
                    default -> false;
                };

        // Return true only if both rank and suit are valid
        return isValidRank && isValidSuit;
    }

    public static Joker parseJokerString(String jokerName) {
        return switch (jokerName.toUpperCase()) {
            case "ODDTODDJOKER" -> new OddToddJoker();
            case "SCARYFACEJOKER" -> new ScaryFaceJoker();
            case "ABSTRACTJOKER" -> new AbstractJoker();
            case "GLUTTONOUSJOKER" -> new GluttonousJoker();
            case "GREEDYJOKER" -> new GreedyJoker();
            case "HALFJOKER" -> new HalfJoker();
            case "LUSTYJOKER" -> new LustyJoker();
            case "WRATHFULJOKER" -> new WrathfulJoker();
            default -> throw new IllegalArgumentException("Invalid Joker name: " + jokerName);
        };
    }

    public static String jokerToString(Joker joker) {
        if (joker == null) {
            throw new IllegalArgumentException("Joker cannot be null");
        }
        return joker.getIdentifierName(); // Returns the class name of the Joker
    }

    public static boolean isValidJokerString(String jokerName) {
        if (jokerName == null || jokerName.trim().isEmpty()) {
            return false; // Null or empty string is not valid
        }

        return switch (jokerName.toUpperCase()) {
            case "ODDTODDJOKER",
                    "SCARYFACEJOKER",
                    "ABSTRACTJOKER",
                    "GLUTTONOUSJOKER",
                    "GREEDYJOKER",
                    "HALFJOKER",
                    "LUSTYJOKER",
                    "WRATHFULJOKER" -> true;
            default -> false;
        };
    }
}
