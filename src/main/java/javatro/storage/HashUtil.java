package javatro.storage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HashUtil {

    public static String generateHash(List<String> runData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder dataString = new StringBuilder();

            for (int i = 0; i < runData.size(); i++) {
                String data = runData.get(i).trim(); // Trim whitespace for consistency

                if (data.isEmpty() || data.equals("-")) {
                    data = "NA"; // Normalize empty entries to a consistent value
                }

                dataString.append(data);

                // Avoid adding a trailing comma at the end
                if (i < runData.size() - 1) {
                    dataString.append(",");
                }
            }

            byte[] hash = digest.digest(dataString.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
