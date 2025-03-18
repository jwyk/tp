package javatro_view;

import javatro_manager.LoadStartScreenCommand;

/**
 * The {@code OptionScreen} class represents the options menu screen in the application. It provides
 * options for enabling emotes and returning to the main menu.
 */
public class OptionScreen extends Screen {

    /** Constructs an {@code OptionScreen} with a predefined title and command mappings. */
    public OptionScreen() {
        super("OPTIONS MENU"); // Sets the menu name (where users select options)
        commandMap.add(
                new LoadStartScreenCommand()); // Adding option to go back to the start screen
    }

    /** Prints the welcome message for the options screen. */
    private void printSign() {
        System.out.println("WELCOME TO OPTIONS MENU");
    }

    /** Displays the options menu screen. */
    @Override
    public void displayScreen() {
        printSign();
    }
}
