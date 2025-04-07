package display.screens;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

import javatro.audioplayer.AudioPlayer;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.StartScreen;
import javatro.manager.JavatroManager;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.HelpMenuOption;
import javatro.manager.options.RunSelectOption;
import javatro.manager.options.Option;
import javatro.utilities.csvutils.CSVUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class CardSelectScreenTest extends ScreenTest {
    @BeforeEach
    public void setUp() {

        try {
            CSVUtils.writeSampleToCSV(SAVEFILE_PATH, SAMPLE_DATA);
        } catch (IOException e) {
            fail("Failed To Write To CSV: " + e);
        }

        super.setUp();
        try {
            JavatroManager.setScreen(new StartScreen());
        } catch (JavatroException e) {
            System.out.println("Failed to Set Screen: " + e.getMessage());
        }
    }

    @Test
    public void commandMatchCheck() {
        expectedCommands.add(RunSelectOption.class);
        expectedCommands.add(HelpMenuOption.class);
        expectedCommands.add(ExitGameOption.class);

        List<Option> actualCommands = UI.getCurrentScreen().getCommandMap();

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
