// author @@flyingapricot
package javatro.display.screens;

import static javatro.display.UI.*;

import javatro.core.JavatroException;
import javatro.display.formatter.runselect.ArtConstants;
import javatro.display.formatter.runselect.DisplayFormatter;
import javatro.manager.options.*;
import javatro.storage.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunSelectScreen extends Screen {

    private static int runNumber = 1;
    private final Storage storage = Storage.getStorageInstance();

    /**
     * Constructs a screen with specified options menu title.
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

    @Override
    public void displayScreen() {
        if (storage.getNumberOfRuns() > 0) {
            List<String> optionLines = DisplayFormatter.formatRunInformation(storage, runNumber);
            printBorderedContent("RUN #" + runNumber, optionLines);
        } else {
            noSavedRunsDisplay();
        }
    }

    public static int getRunNumber() {
        return runNumber;
    }

    public static void setRunNumber(int runNumber) {
        RunSelectScreen.runNumber = runNumber;
    }

    String stripAnsi(String input) {
        return input.replaceAll("\\u001B\\[[;\\d]*m", "");
    }
}
