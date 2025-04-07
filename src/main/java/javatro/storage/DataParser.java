package javatro.storage;

import javatro.storage.utils.CardUtils;
import javatro.storage.utils.HashUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DataParser {

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

    static final Set<String> VALID_DECKS = Set.of("RED", "ABANDONED", "CHECKERED", "BLUE");
    static final Set<String> VALID_BLINDS =
            Set.of("SMALL BLIND", "LARGE BLIND", "BOSS BLIND");

    public static boolean isCSVDataValid(String csvRawData) {

        String[] rows =
                csvRawData.split(
                        "\\r?\\n"); // Split by newline, handling Windows and Unix line endings

        for (String row : rows) {
            row = row.trim();

            if (row.isEmpty()) continue; // Skip empty lines

            String[] columns = row.split(",");

            // Get the stored hash (last column)
            String storedHash = columns[columns.length - 1];

            // Extract the actual game data
            List<String> rowData = new ArrayList<>(Arrays.asList(columns));
            rowData.remove(rowData.size() - 1); // Remove the hash column before validation

            // Normalize the row data before hashing
            List<String> normalizedRowData = new ArrayList<>();
            for (String data : rowData) {
                data = data.trim();
                if (data.equals("-") || data.isEmpty()) {
                    data = "NA";
                }
                normalizedRowData.add(data);
            }

            // Generate a hash from the normalized data
            String computedHash = HashUtil.generateHash(normalizedRowData);

            // Compare hashes
            if (!computedHash.equals(storedHash)) {
                System.out.println("Invalid row data detected due to hash mismatch: " + row);
                return false;
            }

            // Additional validation (Existing Logic)
            if (columns.length < DataParser.HIGH_CARD_INDEX + 13) {
                System.out.println("Invalid number of columns in row: " + row);
                return false;
            }

            // Check if deck name is valid
            String deckName = columns[DataParser.DECK_INDEX].trim();
            if (!VALID_DECKS.contains(deckName)) {
                System.out.println("Invalid deck name in row: " + row);
                return false;
            }

            // Check if blind name is valid
            String blindName = columns[DataParser.BLIND_INDEX].trim();
            if (!VALID_BLINDS.contains(blindName)) {
                System.out.println("Invalid blind name in row: " + row);
                return false;
            }

            // Validate predefined numeric columns
            try {
                int[] numericIndexes = {
                        DataParser.RUN_NUMBER_INDEX,
                        DataParser.ROUND_NUMBER_INDEX,
                        DataParser.ROUND_SCORE_INDEX,
                        DataParser.HAND_INDEX,
                        DataParser.DISCARD_INDEX,
                        DataParser.WINS_INDEX,
                        DataParser.LOSSES_INDEX
                };

                for (int index : numericIndexes) {
                    Integer.parseInt(columns[index]);
                }

                // Validate Ante Number (must be between 1 and 8)
                int anteNumber = Integer.parseInt(columns[DataParser.ANTE_NUMBER_INDEX]);
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
            for (int i = DataParser.HOLDING_HAND_START_INDEX; i < DataParser.JOKER_HAND_START_INDEX; i++) {
                String card = columns[i].trim();
                if (!card.equals("NA") && !CardUtils.isValidCardString(card)) {
                    System.out.println("Invalid holding card: " + card);
                    return false;
                }
            }

            // Validate joker hands (Fixed 5 slots)
            for (int i = DataParser.JOKER_HAND_START_INDEX; i < DataParser.HIGH_CARD_INDEX; i++) {
                String joker = columns[i].trim();
                if (!joker.equals("NA") && !CardUtils.isValidJokerString(joker)) {
                    System.out.println("Invalid joker card: " + joker);
                    return false;
                }
            }

            // Validate planet card levels (From PLANET_CARD_START_INDEX onwards)
            for (int i = DataParser.HIGH_CARD_INDEX; i < DataParser.START_OF_REST_OF_DECK; i++) {
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
            for (int i = DataParser.START_OF_REST_OF_DECK; i < DataParser.START_OF_REST_OF_DECK + 44; i++) {
                String card = columns[i].trim();
                if (!card.equals("NA") && !CardUtils.isValidCardString(card)) {
                    System.out.println("Invalid rest of the deck card: " + card);
                    return false;
                }
            }
        }

        return true; // All rows are valid
    }

    public static void loadCSVData(String csvRawData) {
        String[] runs = csvRawData.split("\\r?\\n");
        StorageManager storageManager = StorageManager.getInstance();

        for (int i = 0; i < runs.length; i++) {
            String[] runInfo = runs[i].split(",");
            ArrayList<String> runInfoList = new ArrayList<>(Arrays.asList(runInfo));
            storageManager.saveRunData(i, runInfoList);
        }

    }

}
