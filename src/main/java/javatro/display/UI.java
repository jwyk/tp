package javatro.display;

import javatro.core.JavatroException;
import javatro.display.screens.*;

/**
 * The {@code display} class is responsible for managing and displaying different screens in the
 * application. It handles user input, manages screen transitions, and notifies observers of user
 * actions. This class follows the Singleton pattern to ensure a single instance manages all
 * display-related operations.
 */
public class UI {

    /** The current screen being displayed to the user. */
    private static Screen currentScreen;

    /** Predefined game-related screens. */
    private static final GameScreen GAME_SCREEN;

    private static final DiscardScreen DISCARD_SCREEN;
    private static final PlayScreen PLAY_SCREEN;
    private static final HelpScreen HELP_SCREEN;
    private static final StartScreen START_SCREEN;

    /** Parser instance for handling user input. */
    private static final Parser PARSER = new Parser();

    static {
        try {
            GAME_SCREEN = new GameScreen();
            DISCARD_SCREEN = new DiscardScreen();
            PLAY_SCREEN = new PlayScreen();
            HELP_SCREEN = new HelpScreen();
            START_SCREEN = new StartScreen();
        } catch (JavatroException e) {
            System.err.println("Failed to initialize screens: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize screens", e);
        }
    }

    // region Printing Message
    /** display-related constants for display formatting. */
    public static final String END = "\033[0m";

    public static final String BOLD = "\033[1m";
    public static final String ITALICS = "\033[3m";
    public static final String UNDERLINE = "\033[4m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String PURPLE = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";

    /** Fixed width for the bordered message display. */
    private static final int MESSAGE_WIDTH = 100;

    /** Custom border characters */
    private static final char TOP_LEFT = '╔';

    private static final char TOP_RIGHT = '╗';
    private static final char BOTTOM_LEFT = '╚';
    private static final char BOTTOM_RIGHT = '╝';
    private static final char HORIZONTAL = '═';
    private static final char VERTICAL = '║';

    /**
     * Prints a bordered message with a title and content.
     *
     * @param title the title of the message
     * @param lines the content lines to display
     */
    public static void printBorderedMessage(String title, String[] lines) {
        // Top border
        System.out.print(TOP_LEFT);
        System.out.print(String.valueOf(HORIZONTAL).repeat(MESSAGE_WIDTH - 2));
        System.out.println(TOP_RIGHT);

        // Centered title
        System.out.println(centerText(BOLD + title + END));

        // Middle border
        System.out.print(VERTICAL);
        System.out.print(String.valueOf(HORIZONTAL).repeat(MESSAGE_WIDTH - 2));
        System.out.println(VERTICAL);

        // display lines
        for (String line : lines) {
            System.out.println(centerText(line));
        }

        // Bottom border
        System.out.print(BOTTOM_LEFT);
        System.out.print(String.valueOf(HORIZONTAL).repeat(MESSAGE_WIDTH - 2));
        System.out.println(BOTTOM_RIGHT);
    }

    /**
     * Centers the given text within a specified width, padding it with spaces on both sides.
     *
     * @param text the text to center
     * @return the centered text surrounded by borders
     */
    private static String centerText(String text) {
        // Remove ANSI escape codes to calculate the actual display length
        String strippedText = text.replaceAll("\033\\[[;\\d]*m", "");
        int displayLength = strippedText.length();

        // Calculate padding
        int paddingSize = (MESSAGE_WIDTH - displayLength - 2) / 2;

        // Format the centered text with borders
        return String.format(
                "%c%"
                        + paddingSize
                        + "s%s%"
                        + (MESSAGE_WIDTH - displayLength - paddingSize - 2)
                        + "s%c",
                VERTICAL,
                "",
                text,
                "",
                VERTICAL);
    }
    // endregion

    /**
     * Sets the current screen and displays it.
     *
     * @param screen the screen to be displayed
     * @throws IllegalArgumentException if the provided screen is {@code null}
     */
    public void setCurrentScreen(Screen screen) throws JavatroException {
        if (screen == null) {
            throw JavatroException.invalidScreen();
        }
        System.out.println("Transitioning to: " + screen.getClass().getSimpleName());
        currentScreen = screen;
        currentScreen.displayScreen();
        PARSER.getInput(); // This will handle retries internally
    }

    /**
     * Gets the current screen being displayed.
     *
     * @return the current {@link Screen}
     */
    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    /** Clears the console screen. This method uses ANSI escape codes to clear the console. */
    public static void clearScreen() {
        final String FLUSH = "\033[H\033[2J";
        System.out.print(FLUSH);
        System.out.flush(); // Ensure the output is flushed
    }

    /**
     * Gets the parser instance for handling user input.
     *
     * @return the {@link Parser} instance
     */
    public static Parser getParser() {
        return PARSER;
    }

    // region Screen Getters
    /**
     * Gets the screen where users select cards to discard.
     *
     * @return the {@link DiscardScreen} instance
     */
    public static DiscardScreen getDiscardScreen() {
        return DISCARD_SCREEN;
    }

    /**
     * Gets the screen where users select cards to play.
     *
     * @return the {@link PlayScreen} instance
     */
    public static PlayScreen getPlayScreen() {
        return PLAY_SCREEN;
    }

    /**
     * Gets the game screen.
     *
     * @return the {@link GameScreen} instance
     */
    public static GameScreen getGameScreen() {
        return GAME_SCREEN;
    }

    /**
     * Gets the start screen.
     *
     * @return the {@link StartScreen} instance
     */
    public static StartScreen getStartScreen() {
        return START_SCREEN;
    }

    /**
     * Gets the help screen.
     *
     * @return the {@link HelpScreen} instance
     */
    public static HelpScreen getHelpScreen() {
        return HELP_SCREEN;
    }
    // endregion
}
