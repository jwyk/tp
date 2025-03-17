package javatro_view;

import javatro_manager.Command;

import java.util.ArrayList;

/**
 * The {@code Screen} class serves as an abstract base class for all screens in the application. It
 * defines common behaviors such as displaying options for the user to select and handling commands.
 */
public abstract class Screen {

    /** A list of commands associated with this screen. */
    protected final ArrayList<Command> commandMap = new ArrayList<>();

    /** The title of the options menu displayed on the screen. */
    private String optionsTitle;

    /**
     * Constructs a screen with the specified options title.
     *
     * @param optionsTitle the title of the options menu
     */
    public Screen(String optionsTitle) {
        this.optionsTitle = optionsTitle;
    }

    /** Displays the screen content. This method must be implemented by subclasses. */
    public abstract void displayScreen();

    /** Displays the available options in a formatted menu style. */
    protected void displayOptions() {
        int width = 30; // Fixed width for the menu
        String border = "*".repeat(width);
        int paddingSize = (width - optionsTitle.length() - 2) / 2;
        String paddedTitle =
                "*"
                        + " ".repeat(paddingSize)
                        + optionsTitle
                        + " ".repeat(width - optionsTitle.length() - paddingSize - 2)
                        + "*";

        System.out.println(border);
        System.out.println(paddedTitle);
        System.out.println(border);

        int count = 1;
        for (Command s : commandMap) {
            System.out.printf("*  [%d] %s%n", count, s.getDescription());
            count++;
        }
        System.out.println(border);
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
     * @param index the index of the command
     * @return the command at the specified index
     */
    public Command getCommand(int index) {
        return commandMap.get(index);
    }
}
