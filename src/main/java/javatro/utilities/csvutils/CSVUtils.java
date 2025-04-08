package javatro.utilities.csvutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CSVUtils {

    /**
     * Clears the content of a CSV file by overwriting it with an empty string.
     *
     * @param filePath The path to the CSV file to be cleared.
     * @throws IOException If an I/O error occurs.
     */
    public static void clearCSVFile(String filePath) throws IOException {
        Path saveFilePath = Paths.get(filePath);
        Files.deleteIfExists(saveFilePath);
        Files.createFile(saveFilePath);
    }

    /**
     * Writes a sample string to a CSV file.
     *
     * @param filePath The path to the CSV file to write to.
     * @param sampleString The sample string to write to the CSV file.
     * @throws IOException If an I/O error occurs.
     */
    public static void writeSampleToCSV(String filePath, String sampleString) throws IOException {
        Path saveFilePath = Paths.get(filePath);
        Files.write(
                saveFilePath,
                sampleString.getBytes(),
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}
