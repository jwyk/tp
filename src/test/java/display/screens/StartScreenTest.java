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
        super.setUp();
    }

    @Test
    public void commandMatchCheck() {
        expectedCommands.add(LoadRunSelectOption.class);
        expectedCommands.add(HelpMenuOption.class);
        expectedCommands.add(ExitGameOption.class);


        List<Option> actualCommands = UI.getStartScreen().getCommandMap();

        compareCommandListTypes(expectedCommands, actualCommands);
    }

}
