package javatro.display.screens;

import javatro.core.*;
import javatro.core.jokers.Joker;
import javatro.core.jokers.JokerFactory;
import javatro.display.UI;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.NextRoundOption;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static javatro.display.UI.*;

public class WinRoundScreen extends Screen {

//    /**
//     * Variable to hold the jimbo content. This is used to display a visually appealing
//     * welcome message. The logo is loaded from an external file during class initialization.
//     */
//    private static String JIMBO;
//
//    // Static block to initialize the jimbo logo from a file
//    static {
//        try (InputStream inputStream =
//                     StartScreen.class.getResourceAsStream("/javatro/display/ansi/jimbo.txt")) {
//            if (inputStream == null) {
//                throw JavatroException.errorLoadingLogo("jimbo.txt");
//            }
//            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
//                JIMBO = scanner.useDelimiter("\\A").next(); // Read the entire file
//            }
//        } catch (IOException | JavatroException e) {
//            JIMBO = "JIMBO"; // Fallback in case of error
//            System.err.println(JavatroException.errorLoadingLogo("jimbo.txt").getMessage());
//        }
//    }

    /**
     * Constructs a screen with the specified options title.
     *
     * @throws JavatroException if the options title is null or empty
     */
    public WinRoundScreen() throws JavatroException {
        super("!" + GREEN + " You beat the Blind " + END + BLACK_B + "!");
        commandMap.add(new NextRoundOption());
        commandMap.add(new ExitGameOption());
    }

    /**
     * Displays the screen content. This method must be implemented by subclasses to define the
     * specific behavior and layout of the screen.
     */
    @Override
    public void displayScreen() {

        // Give the player a random joker card if boss blind is beaten
        if (JavatroCore.getAnte().getBlind() == Ante.Blind.BOSS_BLIND) {
            Joker randomJoker = JokerFactory.createRandomJoker();

            String title =
                    "You got a free " + CYAN + BOLD
                            + randomJoker.getName()
                            + END + BLACK_B + " card!";

            try {
                JavatroCore.heldJokers.add(randomJoker);
                UI.printBorderedContent(title, List.of(randomJoker.getDescription()));
            } catch (JavatroException e) {
                UI.printBorderedContent(title, List.of("Limit for Jokers has been reached!"));
            }

        // Give the player a random free planet card if normal blinds are beaten
        } else {
            PokerHand.HandType[] handTypes = PokerHand.HandType.values();
            Random random = new Random();
            PokerHand.HandType randomPlanetCard = handTypes[random.nextInt(handTypes.length)];
            PlanetCard.getForHand(randomPlanetCard).apply();

            String title =
                    "You got a free " + BLUE + BOLD
                            + PlanetCard.getForHand(randomPlanetCard).getName()
                            + END + BLACK_B + " card!";

            String[] lines = {
                    String.format("%s%s<%s>%s%s Poker Hand Leveled Up!", BOLD, YELLOW, randomPlanetCard.getHandName(), END, BLACK_B),
                    String.format("Level %s: %s+%d Chips%s%s and %s+%d Mult%s%s",
                            PlanetCard.getLevel(randomPlanetCard),
                            BLUE, PlanetCard.getChipIncrement(randomPlanetCard), END, BLACK_B,
                            RED, PlanetCard.getMultiIncrement(randomPlanetCard), END, BLACK_B)
            };
            UI.printBorderedContent(title, List.of(lines));
        }
    }
}
