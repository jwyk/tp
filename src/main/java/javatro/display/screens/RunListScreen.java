// @@author flyingapricot
package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.ViewRunOption;
import javatro.storage.Storage;

/**
 * Represents the screen that displays the list of saved game runs.
 *
 * <p>This screen is responsible for:
 *
 * <ul>
 *   <li>Fetching the list of saved game runs from {@link Storage}
 *   <li>Dynamically generating menu options for each saved run
 *   <li>Providing a user interface for selecting a specific run to view
 * </ul>
 *
 * <p>Extends {@link Screen} and overrides its display handling mechanism.
 */
public class RunListScreen extends Screen {

    /**
     * Constructs the RunListScreen by retrieving the saved runs from storage and creating
     * corresponding {@link ViewRunOption} objects for each run.
     *
     * <p>The options are then added to the command map for user interaction.
     *
     * @throws JavatroException if there is an error initializing the screen, such as issues with
     *     accessing the {@link Storage} instance or fetching run data.
     */
    public RunListScreen() throws JavatroException {
        super("List of Saved Runs");

        // Retrieve the singleton instance of Storage
        Storage storage = Storage.getStorageInstance();

        // Fetch the total number of saved runs
        int numberOfRuns = storage.getNumberOfRuns();

        // Create menu options for each run and add them to the command map
        for (int i = 1; i <= numberOfRuns; i++) {
            ViewRunOption newRunOption = new ViewRunOption();
            newRunOption.setRunNumber(i);
            super.commandMap.add(newRunOption);
        }
    }

    /**
     * Displays the list of saved runs on the screen.
     *
     * <p>This method is responsible for rendering:
     *
     * <ul>
     *   <li>The screen title: "List of Saved Runs"
     *   <li>Menu options dynamically generated from saved run data
     * </ul>
     *
     * <p>Actual rendering of the menu options is handled by the superclass.
     */
    @Override
    public void displayScreen() {}
}
