// @@author flyingapricot
package javatro.storage;

import javatro.storage.utils.HashUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * The {@code DataParser} class provides static utility methods for parsing, validating, and loading
 * CSV data related to game runs. This class is intended to work with the {@link StorageManager} and
 * does not maintain its own state.
 *
 * <p>All methods in this class are static, making it a utility class. Constants related to CSV
 * parsing are defined here for easy access.
 */
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
    public static final int HOLDING_HAND_START_INDEX = 10;
    public static final int JOKER_HAND_START_INDEX = 18;

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
    static final Set<String> VALID_BLINDS = Set.of("SMALL BLIND", "LARGE BLIND", "BOSS BLIND");

    /** Private constructor to prevent instantiation of this utility class. */
    private DataParser() {}

    /**
     * Validates the provided CSV data for structural integrity, hash consistency, and logical
     * validity.
     *
     * @param csvRawData The raw CSV data as a {@code String}.
     * @return {@code true} if all rows in the CSV data are valid, otherwise {@code false}.
     */
    public static boolean isCSVDataValid(String csvRawData) {
        assert csvRawData != null : "CSV raw data must not be null";
        assert !csvRawData.trim().isEmpty() : "CSV raw data must not be empty";

        String[] rows = csvRawData.split("\\r?\\n");

        for (String row : rows) {
            row = row.trim();
            if (row.isEmpty()) {
                continue;
            }

            String[] columns = row.split(",");
            assert columns.length > 0 : "Columns should not be empty for a row.";

            String storedHash = columns[columns.length - 1];
            List<String> rowData = new ArrayList<>(Arrays.asList(columns));
            rowData.remove(rowData.size() - 1);

            assert storedHash != null && !storedHash.isEmpty()
                    : "Stored hash must not be null or empty.";

            List<String> normalizedRowData = new ArrayList<>();
            for (String data : rowData) {
                data = data.trim();
                if (data.equals("-") || data.isEmpty()) {
                    data = "NA";
                }
                normalizedRowData.add(data);
            }

            String computedHash = HashUtil.generateHash(normalizedRowData);
            assert !computedHash.isEmpty() : "Computed hash must not be null or empty.";

            if (!computedHash.equals(storedHash)) {
                System.out.println("Invalid row data detected due to hash mismatch: " + row);
                return false;
            }

            if (columns.length < DataParser.HIGH_CARD_INDEX + 13) {
                System.out.println("Invalid number of columns in row: " + row);
                return false;
            }

            String deckName = columns[DataParser.DECK_INDEX].trim();
            if (!VALID_DECKS.contains(deckName)) {
                System.out.println("Invalid deck name in row: " + row);
                return false;
            }

            String blindName = columns[DataParser.BLIND_INDEX].trim();
            if (!VALID_BLINDS.contains(blindName)) {
                System.out.println("Invalid blind name in row: " + row);
                return false;
            }

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
                    assert columns[index] != null : "Numeric index must not be null.";
                    Integer.parseInt(columns[index]);
                }

                int anteNumber = Integer.parseInt(columns[DataParser.ANTE_NUMBER_INDEX]);
                assert anteNumber >= 1 && anteNumber <= 8 : "Ante Number must be between 1 and 8.";

            } catch (NumberFormatException e) {
                System.out.println("Invalid numeric value in row: " + row);
                return false;
            }
        }
        return true;
    }

    /**
     * Loads the CSV data into the {@link StorageManager}.
     *
     * @param csvRawData The raw CSV data as a {@code String}.
     */
    public static void loadCSVData(String csvRawData) {
        assert csvRawData != null : "CSV raw data must not be null.";
        assert !csvRawData.trim().isEmpty() : "CSV raw data must not be empty.";

        String[] runs = csvRawData.split("\\r?\\n");
        StorageManager storageManager = StorageManager.getInstance();

        for (int i = 0; i < runs.length; i++) {
            String[] runInfo = runs[i].split(",");
            assert runInfo.length > 0 : "Run info should not be empty.";

            ArrayList<String> runInfoList = new ArrayList<>(Arrays.asList(runInfo));
            storageManager.saveRunData(i, runInfoList);
        }
    }
}
