// @@author flyingapricot
package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.*;
import javatro.storage.Storage;

/**
 * Initial application screen displaying main menu options and game logo.
 *
 * <p>Provides functionality for:
 *
 * <ul>
 *   <li>Displaying the Javatro logo
 *   <li>Presenting core game startup options
 *   <li>Handling basic application navigation
 * </ul>
 */
public class RunListScreen extends Screen {
    /**
     * Constructs the jump to run screen with default main menu options.
     *
     * @throws JavatroException if screen initialization fails
     */
    public RunListScreen() throws JavatroException {
        super("List of Saved Runs");

        Storage storage = Storage.getStorageInstance();
        int numberOfRuns = storage.getNumberOfRuns();
//        super.commandMap.add(new RunSelectOption());

        for (int i = 1; i <= numberOfRuns; i++) {
            ViewRunOption newRunOption = new ViewRunOption();
            newRunOption.setRunNumber(i);
            super.commandMap.add(newRunOption);
        }
        // assert commandMap.size() == 3 : "StartScreen should have exactly 3 initial options";
    }

    /**
     * Displays the startup screen content including:
     *
     * <ul>
     *   <li>Javatro logo loaded from external file
     *   <li>Formatted menu options (handled by superclass)
     * </ul>
     */
    @Override
    public void displayScreen() {}
}
