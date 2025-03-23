package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Screen} class serves as an abstract base class for all screens in the application.
 * It defines common behaviors such as displaying options for the user to select and handling commands.
 * Subclasses must implement the {@link #displayScreen()} method to define their specific content.
 */
public abstract class Screen {

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

    /** A list of commands associated with this screen. */
    protected final List<Option> commandMap = new ArrayList<>();

    /** The title of the options menu displayed on the screen. */
    private final String optionsTitle;

    /** Fixed width for the options menu display. */
    private static final int MENU_WIDTH = 100;

    /** Custom border characters */
    private final char TOP_LEFT = '╔';
    private final char TOP_RIGHT = '╗';
    private final char BOTTOM_LEFT = '╚';
    private final char BOTTOM_RIGHT = '╝';
    private final char HORIZONTAL = '═';
    private final char VERTICAL = '║';
    private final char CROSS = '╬';
    private final char T_UP = '╩';
    private final char T_DOWN = '╦';
    private final char T_LEFT = '╣';
    private final char T_RIGHT = '╠';

    /**
     * Constructs a screen with the specified options title.
     *
     * @param optionsTitle the title of the options menu (cannot be null or empty)
     * @throws JavatroException if the options title is null or empty
     */
    public Screen(String optionsTitle) throws JavatroException {
        if (optionsTitle == null || optionsTitle.trim().isEmpty()) {
            throw JavatroException.invalidOptionsTitle();
        }
        this.optionsTitle = String.format("♥️ ♠️ \uD83C\uDCCF %s%s%s \uD83C\uDCCF ♦️ ♣️", BOLD, optionsTitle.trim(), END);
    }

    /**
     * Displays the screen content. This method must be implemented by subclasses to define
     * the specific behavior and layout of the screen.
     */
    public abstract void displayScreen();

    /**
     * Displays the available options in a formatted menu style.
     * The menu includes a border, a centered title, and a list of options with descriptions.
     */
    public void displayOptions() {
        // Top border
        System.out.print(TOP_LEFT);
        System.out.print(String.valueOf(HORIZONTAL).repeat(MENU_WIDTH - 2));
        System.out.println(TOP_RIGHT);

        // Centered title
        System.out.println(centerText(optionsTitle));

        // Middle border
        System.out.print(T_RIGHT);
        System.out.print(String.valueOf(HORIZONTAL).repeat(MENU_WIDTH - 2));
        System.out.println(T_LEFT);

        // display options 《%d》 【%d】 『%d』 「%d」
        for (int i = 0; i < commandMap.size(); i++) {
            String option = String.format("%s[%d]%s %s%s%s", BOLD, i + 1, END, ITALICS, commandMap.get(i).getDescription(), END);
            System.out.println(centerText(option));
        }

        // Bottom border
        System.out.print(BOTTOM_LEFT);
        System.out.print(String.valueOf(HORIZONTAL).repeat(MENU_WIDTH - 2));
        System.out.println(BOTTOM_RIGHT);
    }

    /**
     * Centers the given text within a specified width, padding it with spaces on both sides.
     * This version handles ANSI escape codes and Unicode characters correctly.
     *
     * @param text the text to center
     * @return the centered text surrounded by borders
     */
    private String centerText(String text) {
        // Remove ANSI escape codes to calculate the actual display length
        String strippedText = text.replaceAll("\033\\[[;\\d]*m", "");
        int displayLength = strippedText.length();

        // Calculate padding
        int paddingSize = (MENU_WIDTH - displayLength - 2) / 2;

        // Format the centered text with borders
        return String.format(
                "%c%" + paddingSize + "s%s%" + (MENU_WIDTH - displayLength - paddingSize - 2) + "s%c",
                VERTICAL, "", text, "", VERTICAL
        );
    }

    /**
     * Returns the number of available options (commands) in this screen.
     *
     * @return the number of options available
     */
    public int getOptionsSize() {
        return commandMap.size();
    }

    /**
     * Retrieves the command associated with the given index.
     *
     * @param index the index of the command (0-based)
     * @return the command at the specified index
     * @throws JavatroException if the index is out of bounds
     */
    public Option getCommand(int index) throws JavatroException {
        if (index < 0 || index >= commandMap.size()) {
            throw JavatroException.indexOutOfBounds(index);
        }
        return commandMap.get(index);
    }
}