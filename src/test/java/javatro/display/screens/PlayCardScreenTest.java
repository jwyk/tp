package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.options.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayCardScreenTest extends ScreenTest {

    private PlayCardScreen playCardScreen;

    @BeforeEach
    public void setUp() {
        try {
            playCardScreen = new PlayCardScreen();
            super.setUp();
        } catch (JavatroException e) {
            fail("Failed to initialize PlayCardScreen: " + e.getMessage());
        }
    }

    @Test
    public void testScreenInitialization() {
        assertDoesNotThrow(PlayCardScreen::new, "PlayCardScreen should initialize without errors.");
    }

    @Test
    public void testCommandMapContainsExpectedOptions() {
        List<Option> actualCommands = playCardScreen.getCommandMap();

        // Expected options for PlayCardScreen
        List<Class<?>> expectedCommands = new ArrayList<>();
        expectedCommands.add(CardSelectOption.class);
        expectedCommands.add(SortBySuitOption.class);
        expectedCommands.add(SortByRankOption.class);
        expectedCommands.add(PokerHandOption.class);
        expectedCommands.add(DeckViewOption.class);
        expectedCommands.add(ResumeGameOption.class);

        compareCommandListTypes(expectedCommands, actualCommands);
    }

}
