package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.MainMenuOption;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static javatro.display.UI.*;

public class LoseScreen extends Screen {

    /**
     * Variable to hold the jimbo content. This is used to display a visually appealing
     * welcome message. The logo is loaded from an external file during class initialization.
     */
    private static String JIMBO;

    // Static block to initialize the jimbo logo from a file
    static {
        try (InputStream inputStream =
                     StartScreen.class.getResourceAsStream("/javatro/display/ansi/jimbo.txt")) {
            if (inputStream == null) {
                throw JavatroException.errorLoadingLogo("jimbo.txt");
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                JIMBO = scanner.useDelimiter("\\A").next(); // Read the entire file
            }
        } catch (IOException | JavatroException e) {
            JIMBO = "JIMBO"; // Fallback in case of error
            System.err.println(JavatroException.errorLoadingLogo("jimbo.txt").getMessage());
        }
    }

    private static final List<String> QUOTES = List.of(
            "Maybe Go Fish is more our speed...",
            "We folded like a cheap suit!",
            "Time for us to shuffle off and try again!",
            "You know what they say, the house always wins!",
            "Looks like we found out who the real Joker is!",
            "Oh no, were you bluffing too?",
            "Looks like the joke's on us!",
            "If I had hands I would have covered my eyes!",
            "I'm literally a fool, what's your excuse?",
            "What a flop!"
    );

    /**
     * Constructs a screen with the specified options title.
     *
     * @throws JavatroException if the options title is null or empty
     */
    public LoseScreen() throws JavatroException {
        super("!" + RED + " GAME OVER " + END + BLACK_B + "!");
        commandMap.add(new MainMenuOption());
        commandMap.add(new ExitGameOption());
    }

    /**
     * Displays the screen content. This method must be implemented by subclasses to define the
     * specific behavior and layout of the screen.
     */
    @Override
    public void displayScreen() {
        System.out.println(JIMBO); // display Jimbo
        String randomQuote = QUOTES.get(ThreadLocalRandom.current().nextInt(QUOTES.size()));

        // Top border
        printBlackB(TOP_LEFT + String.valueOf(HORIZONTAL).repeat(BORDER_WIDTH - 2) + TOP_RIGHT);
        System.out.println();

        System.out.println(centerText(RED + BOLD + "HA".repeat(47) + END + BLACK_B, BORDER_WIDTH));
        System.out.println(centerText(ITALICS + randomQuote, BORDER_WIDTH));

        // Bottom border
        printBlackB(
                BOTTOM_LEFT + String.valueOf(HORIZONTAL).repeat(BORDER_WIDTH - 2) + BOTTOM_RIGHT);
        System.out.println();
    }
}
