package javatro.storage.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class HashUtil {

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
