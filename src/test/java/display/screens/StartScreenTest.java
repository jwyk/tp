package display.screens;

// StartScreenTest.java

import javatro.display.UI;
import javatro.manager.options.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
