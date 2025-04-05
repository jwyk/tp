package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;

import java.util.List;

import static javatro.display.UI.BOLD;
import static javatro.display.UI.END;
import static javatro.display.UI.RED;
import static javatro.display.UI.printANSI;

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

    /**
     * Executes the exit game command, displaying a farewell message and terminating the
     * application.
     */
    @Override
    public void execute() throws JavatroException {
        // display the end screen from the file
        printANSI("end_screen.txt");

        // display the farewell message with borders and ANSI formatting
        UI.printBorderedContent(
                BOLD + "::: SEE YOU LATER :::", List.of(RED + "WE KNOW YOU WILL BE BACK SOON" + END));
        System.exit(0); // Terminate the application
    }
}
