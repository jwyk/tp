package javatro.display.screens;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

import javatro.core.JavatroException;
import javatro.manager.options.CardSelectOption;
import javatro.manager.options.DeckViewOption;
import javatro.manager.options.Option;
import javatro.manager.options.PokerHandOption;
import javatro.manager.options.ResumeGameOption;
import javatro.manager.options.SortByRankOption;
import javatro.manager.options.SortBySuitOption;
import javatro.storage.Storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CardSelectScreenTest extends ScreenTest {

    private CardSelectScreen cardSelectScreen;

    @BeforeEach
    public void setUp() {
        try {
            cardSelectScreen = new CardSelectScreenImpl("TEST CARD SELECT SCREEN");
            Storage.saveActive = false;
            super.setUp();
        } catch (JavatroException e) {
            fail("Failed to initialize CardSelectScreen: " + e.getMessage());
        }
    }

    @Test
    public void testScreenInitialization() {
        assertDoesNotThrow(
                () -> new CardSelectScreenImpl("TEST CARD SELECT SCREEN"),
                "CardSelectScreen should initialize without errors.");
    }

    @Test
    public void testCommandMapContainsExpectedOptions() {
        List<Option> actualCommands = cardSelectScreen.getCommandMap();

        // Expected options for CardSelectScreen
        List<Class<?>> expectedCommands = new ArrayList<>();
        expectedCommands.add(CardSelectOption.class);
        expectedCommands.add(SortBySuitOption.class);
        expectedCommands.add(SortByRankOption.class);
        expectedCommands.add(PokerHandOption.class);
        expectedCommands.add(DeckViewOption.class);
        expectedCommands.add(ResumeGameOption.class);

        compareCommandListTypes(expectedCommands, actualCommands);
    }

    // A concrete implementation for the abstract class to enable testing
    private static class CardSelectScreenImpl extends CardSelectScreen {
        public CardSelectScreenImpl(String optionsTitle) throws JavatroException {
            super(optionsTitle);
        }

        @Override
        public void displayScreen() {
            // No-op for testing purposes
        }
    }
}
