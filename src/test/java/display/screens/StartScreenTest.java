package display.screens;

// StartScreenTest.java
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

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

public class StartScreenTest extends ScreenTest {

    @BeforeEach
    public void setUp() {

        try {
            CSVUtils.writeSampleToCSV(SAVEFILE_PATH, SAMPLE_DATA);
        } catch (IOException e) {
            fail("Failed To Write To CSV: " + e);
        }

        super.setUp();

    }

    @Test
    public void testCommandMatchWithSave() {
        try {
            JavatroManager.ui.setCurrentScreen(UI.getStartScreen());
        } catch (JavatroException e) {
            throw new RuntimeException(e);
        }
        List<Class<?>> expectedCommands = List.of(
            LoadRunSelectOption.class,
            HelpMenuOption.class,
            ExitGameOption.class
        );

        List<Option> actualCommands = UI.getCurrentScreen().getCommandMap();
        compareCommandListTypes(expectedCommands, actualCommands);
    }

    @Test
    public void testRunSelectScreenOutputWithSave() throws IOException {
        pipeOutputToFile("data.txt", UI.getStartScreen());
        compareOutputToFile2("StartScreen.txt");
    }

}
