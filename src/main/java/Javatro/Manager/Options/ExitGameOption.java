package Javatro.Manager.Options;

import Javatro.Core.JavatroException;
import Javatro.Display.Screens.StartScreen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * The {@code ExitGameOption} class represents a command that terminates the game. When executed, it
 * prints a farewell message and exits the application.
 */
public class ExitGameOption implements Option {

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Exit Game";
    }

    private static String END_SCREEN;

    // Static block to initialize the End Screen from a file
    static {
        try (InputStream inputStream = StartScreen.class.getResourceAsStream("/Javatro/Display/end_screen.txt")) {
            if (inputStream == null) {
                throw JavatroException.errorLoadingLogo("end_screen.txt");
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                END_SCREEN = scanner.useDelimiter("\\A").next(); // Read the entire file
            }
        } catch (IOException | JavatroException e) {
            END_SCREEN = "End Screen"; // Fallback in case of error
            System.err.println(JavatroException.errorLoadingLogo("end_screen.txt").getMessage());
        }
    }

    /**
     * Executes the exit game command, displaying a farewell message and terminating the
     * application.
     */
    @Override
    public void execute() throws JavatroException {
        System.out.println(END_SCREEN); // Display the end screen
        System.out.println("""
        ╔══════════════════════════════════════════════════════════════════════════════════════════════════╗
        ║                                     ♥️ ♠️ 🃏 \033[1mGOODBYE\033[0m 🃏 ♦️ ♣️                                    ║
        ║                                   \033[31mWE KNOW YOU WILL BE BACK SOON\033[0m                                  ║
        ╚══════════════════════════════════════════════════════════════════════════════════════════════════╝
        """);
        System.exit(0);
    }
}
