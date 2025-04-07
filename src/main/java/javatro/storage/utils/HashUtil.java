// @@author flyingapricot
package javatro.storage.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * The {@code HashUtil} class provides utility methods for generating SHA-256 hashes of game run
 * data. It ensures consistent hashing by normalizing input data before processing.
 *
 * <p>This utility class is designed to work with the storage mechanism to validate game data
 * integrity.
 */
public class HashUtil {

    /**
     * Generates a SHA-256 hash from a list of strings representing run data. This method ensures
     * consistency by trimming whitespace, replacing empty or placeholder values with "NA", and
     * concatenating entries with commas before hashing.
     *
     * <p>Assertions:
     *
     * <ul>
     *   <li>The input list must not be {@code null} or empty.
     *   <li>Each data entry must not be {@code null}.
     *   <li>Data entries should not contain commas to prevent corruption of the CSV structure.
     *   <li>The final concatenated data string must not be empty before hashing.
     *   <li>The generated hash must not be empty and should have a length of 64 characters (for
     *       SHA-256).
     * </ul>
     *
     * @param runData A {@code List} of {@code String} representing the data to be hashed.
     * @return A {@code String} containing the hexadecimal representation of the SHA-256 hash.
     * @throws RuntimeException if the hashing algorithm is not available on the platform.
     */
    public static String generateHash(List<String> runData) {
        assert runData != null : "Input data list cannot be null";
        assert !runData.isEmpty() : "Input data list cannot be empty";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder dataString = new StringBuilder();

            for (int i = 0; i < runData.size(); i++) {
                String data = runData.get(i);
                assert data != null : "Data entries cannot be null";

                data = data.trim(); // Trim whitespace for consistency

                if (data.isEmpty() || data.equals("-")) {
                    data = "NA"; // Normalize empty entries to a consistent value
                }

                assert !data.contains(",") : "Data entries should not contain commas";

                dataString.append(data);

                // Avoid adding a trailing comma at the end
                if (i < runData.size() - 1) {
                    dataString.append(",");
                }
            }

            assert !dataString.isEmpty() : "Data string cannot be empty before hashing";

            byte[] hash = digest.digest(dataString.toString().getBytes(StandardCharsets.UTF_8));
            assert hash.length > 0 : "Generated hash cannot be empty";

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            String result = hexString.toString();
            assert result.length() == 64 : "SHA-256 hash must be 64 characters long";

            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
