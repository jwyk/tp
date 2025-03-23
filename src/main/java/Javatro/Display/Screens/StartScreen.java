package Javatro.Display.Screens;

import Javatro.Core.JavatroException;
import Javatro.Manager.Options.ExitGameOption;
import Javatro.Manager.Options.HelpMenuOption;
import Javatro.Manager.Options.StartGameOption;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * The {@code StartScreen} class represents the initial menu screen of the application.
 * It provides options to start a game, access help, or exit the game.
 *
 * <p>This screen displays a visually appealing welcome message, including the Javatro logo,
 * which is loaded from an external file during class initialization.
 */
public class StartScreen extends Screen {

    /**
     * Variable to hold the Javatro logo content.
     * This is used to display a visually appealing welcome message.
     * The logo is loaded from an external file during class initialization.
     */
    private static String JAVATRO_LOGO;

    // Static block to initialize the Javatro logo from a file
    static {
        try (InputStream inputStream = StartScreen.class.getResourceAsStream("/Javatro/Display/javatro_logo.txt")) {
            if (inputStream == null) {
                throw JavatroException.errorLoadingLogo("javatro_logo.txt");
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                JAVATRO_LOGO = scanner.useDelimiter("\\A").next(); // Read the entire file
            }
        } catch (IOException | JavatroException e) {
            JAVATRO_LOGO = "Javatro Logo"; // Fallback in case of error
            System.err.println(JavatroException.errorLoadingLogo("javatro_logo.txt").getMessage());
        }
    }

    /**
     * Constructs a {@code StartScreen} and initializes available commands.
     *
     * @throws JavatroException if the options title is invalid or if there is an error initializing the screen
     */
    public StartScreen() throws JavatroException {
        super("MAIN MENU");
        commandMap.add(new StartGameOption());
        commandMap.add(new HelpMenuOption());
        commandMap.add(new ExitGameOption());
    }

    /**
     * Displays the start screen, including the game logo and available options.
     */
    @Override
    public void displayScreen() {
        System.out.println(JAVATRO_LOGO); // Display the Javatro logo
    }
}