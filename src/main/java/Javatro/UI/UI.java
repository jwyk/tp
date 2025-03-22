package Javatro.UI;

import Javatro.Manager.Parser;
import Javatro.UI.Screens.*;
import java.beans.PropertyChangeSupport;

/**
 * The {@code UI} class is responsible for managing and displaying different screens in the application.
 * It handles user input, manages screen transitions, and notifies observers of user actions.
 * This class follows the Singleton pattern to ensure a single instance manages all UI-related operations.
 */
public class UI {

    /** The current screen being displayed to the user. */
    private static Screen currentScreen;

    /** Predefined game-related screens. */
    private static final GameScreen GAME_SCREEN = new GameScreen();
    private static final DiscardScreen DISCARD_SCREEN = new DiscardScreen();
    private static final PlayScreen PLAY_SCREEN = new PlayScreen();
    private static final HelpScreen HELP_SCREEN = new HelpScreen();
    private static final StartScreen START_SCREEN = new StartScreen();
    private static final HelpScreen HELP_INTRO_SCREEN = new HelpScreen();

    /** Parser instance for handling user input. */
    private static final Parser PARSER = new Parser();

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
     * Sets the current screen and displays it.
     *
     * @param screen the screen to be displayed
     * @throws IllegalArgumentException if the provided screen is {@code null}
     */
    public void setCurrentScreen(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("Screen cannot be null.");
        }
        currentScreen = screen;
        currentScreen.displayScreen();
        PARSER.getInput();
    }

    /**
     * Gets the current screen being displayed.
     *
     * @return the current {@link Screen}
     */
    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Gets the help introduction screen.
     *
     * @return the {@link HelpScreen} instance for the introduction
     */
    public static Screen getHelpIntroScreen() {
        return HELP_INTRO_SCREEN;
    }

    /**
     * Gets the parser instance for handling user input.
     *
     * @return the {@link Parser} instance
     */
    public static Parser getParser() {
        return PARSER;
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

    /**
     * Clears the console screen.
     * This method uses ANSI escape codes to clear the console.
     */
    public static void clearScreen() {
        final String FLUSH = "\033[H\033[2J";
        System.out.print(FLUSH);
        System.out.flush(); // Ensure the output is flushed
    }
}