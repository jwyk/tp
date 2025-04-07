package javatro.display;

import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class OutputUtils {

    /**
     * Pipes the output of the current screen to a file.
     *
     * @param fileName The name of the file to write to (e.g., "StartScreen.txt").
     * @param renderable The renderable screen object that has a render() method.
     * @throws IOException If an I/O error occurs.
     */
    public static void pipeOutputToFile(String fileName, Screen renderable) throws IOException {
        // Prepare to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        JavatroManager javatroManager;
        UI javatroView = new UI();
        JavatroCore javatroCore = new JavatroCore();

        try {
            javatroManager = new JavatroManager(javatroView, javatroCore);
            // Render the screen
            JavatroManager.runningTests = true;
        } catch (JavatroException e) {
            throw new RuntimeException(e);
        }
        try {
            JavatroManager.setScreen(renderable);
        } catch (JavatroException e) {
            throw new RuntimeException(e);
        }
        // Save the captured output to a file
        try (FileOutputStream fos =
                new FileOutputStream("src/test/resources/screens/" + fileName)) {
            fos.write(outContent.toString().getBytes());
        } finally {
            // Reset the standard output
            System.setOut(originalOut);
        }
    }
}
