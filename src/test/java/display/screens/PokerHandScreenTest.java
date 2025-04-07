package display.screens;

import javatro.audioplayer.AudioPlayer;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.StartScreen;
import javatro.manager.JavatroManager;
import javatro.manager.options.*;
import javatro.storage.Storage;
import javatro.utilities.csvutils.CSVUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

public class PokerHandScreenTest extends ScreenTest {
    @BeforeEach
    public void setUp() {

        try {
            CSVUtils.writeSampleToCSV(SAVEFILE_PATH, SAMPLE_DATA);
        } catch (IOException e) {
            fail("Failed To Write To CSV: " + e);
        }

        Storage.getStorageInstance().resetStorage();
        Storage storage = Storage.getStorageInstance();
        storage.setRunChosen(1);

        super.setUp();
        try {
            JavatroManager.beginGame(
                    (Storage.DeckFromKey(
                            storage.getValue(storage.getRunChosen() - 1, Storage.DECK_INDEX))));

            JavatroManager.jc.beginGame();
            JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
            JavatroCore.currentRound.updateRoundVariables();
        } catch (JavatroException e) {
            System.out.println("Failed to Set Screen: " + e.getMessage());
        }
    }

    @Test
    public void commandMatchCheck() {
        expectedCommands.add(ReturnOption.class);

        List<Option> actualCommands = UI.getPokerHandScreen().getCommandMap();

        compareCommandListTypes(expectedCommands, actualCommands);
    }


    // AudioPlayer Handling
    @Test
    public void testAudioHandling() {
        try {
            AudioPlayer.getInstance().stopAudio(); // Stop audio before playing
            assertDoesNotThrow(
                    () -> AudioPlayer.getInstance().playAudio("audioplayer/balatro_theme.wav"),
                    "Audio should play successfully if file exists.");
            AudioPlayer.getInstance().stopAudio(); // Test stopping audio
        } catch (Exception e) {
            fail("Audio handling caused an unexpected exception: " + e.getMessage());
        }
    }

}
