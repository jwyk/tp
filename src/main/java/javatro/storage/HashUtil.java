package javatro.storage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class HashUtil {

    public static String generateHash(List<String> runData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder dataString = new StringBuilder();

            for (String data : runData) {
                dataString.append(data).append(",");
            }

            byte[] hash = digest.digest(dataString.toString().getBytes());
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
