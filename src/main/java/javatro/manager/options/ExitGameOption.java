// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;

import java.util.List;

/**
 * Terminates the game application after displaying exit sequences. Shows farewell message and
 * performs clean shutdown.
 */
public class ExitGameOption implements Option {

    /**
     * {@inheritDoc}
     *
     * @return Constant description "Exit Game"
     */
    @Override
    public String getDescription() {
        return "Exit Game";
    }

    /**
     * {@inheritDoc} Performs orderly application shutdown sequence.
     *
     * @throws JavatroException if any display operation fails
     */
    @Override
    public void execute() throws JavatroException {
        // Display pre-stored ASCII art exit screen
        UI.printANSI("end_screen.txt");

        // Format and display farewell message
        final String header = UI.BOLD + "::: SEE YOU LATER! :::";
        final List<String> message = List.of(UI.RED + "WE KNOW YOU WILL BE BACK SOON ;)" + UI.END);
        UI.printBorderedContent(header, message);

        // Terminate JVM with normal status
        System.exit(0);
    }
}
