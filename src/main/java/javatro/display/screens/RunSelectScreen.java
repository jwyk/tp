package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.LoadJumpToRunScreenOption;
import javatro.manager.options.SeeNextRunOption;
import javatro.manager.options.SeePreviousRun;
import javatro.manager.options.StartGameOption;

public class RunSelectScreen extends Screen{



    private static int runNumber = 0;

    /**
     * Constructs a screen with specified options menu title.
     *
     * @throws JavatroException if optionsTitle is null or empty
     */
    public RunSelectScreen() throws JavatroException {
        super("Select Run To Load");
        super.commandMap.add(new SeeNextRunOption());
        super.commandMap.add(new SeePreviousRun());
        super.commandMap.add(new LoadJumpToRunScreenOption());
    }

    @Override
    public void displayScreen() {
        System.out.println(runNumber);
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        RunSelectScreen.runNumber = runNumber;
    }
}
