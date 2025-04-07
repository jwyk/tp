package display.screens;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.StartScreen;
import javatro.manager.options.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class StartScreenTest extends ScreenTest {

    private StartScreen startScreen;

    @BeforeEach
    public void setUp() {
        super.setUp();
        try {
            startScreen = new StartScreen();
        } catch (JavatroException e) {
            fail("Failed to create start screen" + e);
        }
    }

    @Test
    public void commandMatchCheck() {
        expectedCommands.add(RunSelectOption.class);
        expectedCommands.add(HelpMenuOption.class);
        expectedCommands.add(ExitGameOption.class);

        List<Option> actualCommands = startScreen.getCommandMap();

        compareCommandListTypes(expectedCommands, actualCommands);
    }

}