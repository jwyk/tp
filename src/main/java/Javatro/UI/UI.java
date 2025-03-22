package Javatro.UI;

import Javatro.Parser.Parser;
import Javatro.UI.Screens.GameScreen;
import Javatro.UI.Screens.HelpIntroScreen;
import Javatro.UI.Screens.HelpScreen;
import Javatro.UI.Screens.Screen;
import Javatro.UI.Screens.SelectCardsToDiscardScreen;
import Javatro.UI.Screens.SelectCardsToPlayScreen;
import Javatro.UI.Screens.StartScreen;

import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The {@code UI} class is responsible for managing and displaying different screens in the
 * application. It also handles user input and notifies observers when user actions occur.
 */
public class UI {

    /** The current screen being displayed to the user. */
    private static Screen currentScreen;

    /** Predefined game-related screens. */
    private static final GameScreen gameScreen = new GameScreen(); // Screen where game is displayed

    private static final SelectCardsToDiscardScreen selectCardsToDiscardScreen =
            new SelectCardsToDiscardScreen(); // Screen where users choose cards to discard
    private static final SelectCardsToPlayScreen selectCardsToPlayScreen =
            new SelectCardsToPlayScreen(); // Screen where users choose cards to play
    private static final HelpScreen helpScreen = new HelpScreen(); // Help screen for users
    private static final StartScreen startScreen = new StartScreen(); // Start Menu Screen
    private static final HelpIntroScreen helpIntroScreen =
            new HelpIntroScreen(); // Help Intro Screen

    private static final Parser parser = new Parser();

    /** Property change support for notifying observers of user input changes. */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Gets the screen where users select cards to discard.
     *
     * @return the SelectCardsToDiscardScreen instance
     */
    public static SelectCardsToDiscardScreen getSelectCardsToDiscardScreen() {
        return selectCardsToDiscardScreen;
    }

    /**
     * Gets the screen where users select cards to play.
     *
     * @return the SelectCardsToPlayScreen instance
     */
    public static SelectCardsToPlayScreen getSelectCardsToPlayScreen() {
        return selectCardsToPlayScreen;
    }

    /**
     * Sets the current screen and displays it.
     *
     * @param s the screen to be displayed
     */
    public void setCurrentScreen(Screen s) {
        currentScreen = s;
        currentScreen.displayScreen();
        parser.getInput();
    }

    /**
     * Gets the current screen being displayed.
     *
     * @return the current screen
     */
    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static Screen getHelpIntroScreen() {
        return helpIntroScreen;
    }

    public static Parser getParser() {
        return parser;
    }

    /**
     * Gets the game screen.
     *
     * @return the GameScreen instance
     */
    public static GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Gets the start screen.
     *
     * @return the StartScreen instance
     */
    public static StartScreen getStartScreen() {
        return startScreen;
    }

    public static HelpScreen getHelpScreen() {
        return helpScreen;
    }

    /** Clears the console screen. */
    public static void clearConsole() {
        String FLUSH = "\033[H\033[2J";
        System.out.print(FLUSH);
        System.out.flush();
    }

    /**
     * Prompts the user to select card numbers and returns a list of selected card indices.
     *
     * @param maxCardsAvailable the maximum number of available cards
     * @param maxCardsToSelect the maximum number of cards a user can select
     * @return a list of selected card indices
     */
    public static List<Integer> getCardInput(int maxCardsAvailable, int maxCardsToSelect) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> userInput;

        while (true) {
            System.out.println(
                    "Enter numbers (comma-separated, e.g., 1,2,3) from 1 to "
                            + maxCardsAvailable
                            + " (Allowed to select only "
                            + maxCardsToSelect
                            + "):");

            String input = scanner.nextLine().trim();
            String[] inputArray = input.split(",");

            userInput =
                    Arrays.stream(inputArray)
                            .map(String::trim)
                            .map(
                                    numStr -> {
                                        try {
                                            return Integer.parseInt(numStr) - 1;
                                        } catch (NumberFormatException e) {
                                            return null;
                                        }
                                    })
                            .filter(num -> num != null && num >= 0 && num < maxCardsAvailable)
                            .collect(Collectors.toList());

            if (!userInput.isEmpty()) {
                System.out.println("You selected the numbers: " + userInput);
                break;
            } else {
                System.out.println("Invalid Input! Please enter valid numbers.");
            }
        }
        return userInput;
    }
}
