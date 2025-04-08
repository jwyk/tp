// author @@flyingapricot
package javatro.display.screens;

import static javatro.display.UI.printBorderedContent;

import javatro.core.JavatroException;
import javatro.display.formatter.runselect.ArtConstants;
import javatro.display.formatter.runselect.DisplayFormatter;
import javatro.manager.options.*;
import javatro.storage.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code RunSelectScreen} class represents a screen used to select and load runs
 * within the game. It displays saved runs or prompts the user to start a new run
 * if no runs are found.
 *
 * <p>This class is part of the {@code javatro.display.screens} package and extends
 * the {@link Screen} class, inheriting its basic screen functionalities. It interacts
 * with the {@link Storage} class to retrieve stored runs and displays them using
 * formatted content through the {@link DisplayFormatter} class.
 *
 * <p>Author: @@flyingapricot
 */
public class RunSelectScreen extends Screen {

    private static int runNumber = 1;
    private final Storage storage = Storage.getStorageInstance();

    /**
     * Constructs a {@code RunSelectScreen} with a specified options menu title.
     * If saved runs are found, corresponding options are added to the command map.
     *
     * @throws JavatroException if optionsTitle is null or empty
     */
    public RunSelectScreen() throws JavatroException {
        super("Select Run To Load");
        if (storage.getNumberOfRuns() > 0) {
            super.commandMap.add(new StartRunOption());
            super.commandMap.add(new ViewNextRunOption());
            super.commandMap.add(new ViewPrevRunOption());
            super.commandMap.add(new ViewRunListOption());
        }
        super.commandMap.add(new StartGameOption());
    }

    /**
     * Displays a message indicating that no saved runs are found.
     * Also prints an ASCII art representation when no runs are available.
     */
    private void noSavedRunsDisplay() {
        List<String> contents = new ArrayList<>();

        String[] noSavedRunsArt = ArtConstants.noSavedRunsArt;

        Collections.addAll(contents, noSavedRunsArt);

        contents.add("");
        contents.add(
                "\u001B[1;38;5;117mHmm... It looks lonely here. No saved runs found.\u001B[0m");
        contents.add("\u001B[38;5;81mMaybe your journey is just waiting to begin.\u001B[0m");
        contents.add(
                "\u001B[38;5;178mStart a new run and I'll disappear, leaving only your progress"
                        + " behind...\u001B[0m");
        contents.add("\u001B[0m"); // Reset to avoid color bleeding

        printBorderedContent("NO SAVED RUNS", contents);
    }

    /**
     * Displays the screen content based on whether saved runs are available or not.
     * If runs are found, formatted run information is displayed. Otherwise, a message
     * prompting the user to start a new run is shown.
     */
    @Override
    public void displayScreen() {
        if (storage.getNumberOfRuns() > 0) {
            List<String> optionLines = DisplayFormatter.formatRunInformation(storage, runNumber);
            printBorderedContent("RUN #" + runNumber, optionLines);
        } else {
            noSavedRunsDisplay();
        }
    }

    /**
     * Retrieves the current run number being displayed.
     *
     * @return the current run number
     */
    public static int getRunNumber() {
        return runNumber;
    }

    /**
     * Sets the current run number to be displayed.
     *
     * @param runNumber the run number to set
     */
    public static void setRunNumber(int runNumber) {
        RunSelectScreen.runNumber = runNumber;
    }

}
