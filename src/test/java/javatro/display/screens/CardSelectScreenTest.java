package javatro.display.screens;

import javatro.core.*;
import javatro.core.jokers.HeldJokers;
import javatro.core.round.Round;
import javatro.manager.options.*;
import javatro.display.UI;
import javatro.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertDoesNotThrow(() -> new CardSelectScreenImpl("TEST CARD SELECT SCREEN"), "CardSelectScreen should initialize without errors.");
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
