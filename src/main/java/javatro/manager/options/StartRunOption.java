/**
 * The {@code MainMenuOption} class represents a command that loads the start screen, allowing
 * players to navigate to the main menu.
 */
package javatro.manager.options;

import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.RunSelectScreen;
import javatro.manager.JavatroManager;
import javatro.storage.DataParser;
import javatro.storage.Storage;
import javatro.storage.utils.CardUtils;

/** A command that loads the run selection screen when executed. */
public class StartRunOption implements Option {

    private int runNumber = 0;
    private final Storage storage = Storage.getStorageInstance();
    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        runNumber = RunSelectScreen.getRunNumber();
        return "Continue Run #" + runNumber;
    }

    /** Executes the command to change the screen to the start menu. */
    @Override
    public void execute() throws JavatroException {
        // Update Storage with chosen run number
        storage.setRunChosen(runNumber);
        JavatroManager.beginGame(
                (CardUtils.DeckFromKey(
                        storage.getValue(storage.getRunChosen() - 1, DataParser.DECK_INDEX))));

        JavatroManager.jc.beginGame();
        JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
        JavatroCore.currentRound.updateRoundVariables();
        JavatroManager.setScreen(UI.getGameScreen());
    }
}
