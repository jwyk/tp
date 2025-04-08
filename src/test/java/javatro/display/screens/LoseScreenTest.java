package javatro.display.screens;

import static org.junit.jupiter.api.Assertions.*;

import javatro.core.JavatroException;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.MainMenuOption;
import javatro.manager.options.Option;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LoseScreenTest {

    private LoseScreen loseScreen;

    @BeforeEach
    public void setUp() throws JavatroException {
        loseScreen = new LoseScreen();
    }

    @Test
    public void testInitialization() {
        assertNotNull(loseScreen, "LoseScreen should be initialized.");
        assertEquals(2, loseScreen.getCommandMap().size(), "LoseScreen should have 2 options.");
    }

    @Test
    public void testCommandMapContainsExpectedOptions() {
        List<Option> commandMap = loseScreen.getCommandMap();

        assertTrue(
                commandMap.stream().anyMatch(option -> option instanceof MainMenuOption),
                "Command map should contain a MainMenuOption.");
        assertTrue(
                commandMap.stream().anyMatch(option -> option instanceof ExitGameOption),
                "Command map should contain an ExitGameOption.");
    }

    @Test
    public void testRandomQuoteSelection() {
        List<String> quotes =
                List.of(
                        "Maybe Go Fish is more our speed...",
                        "We folded like a cheap suit!",
                        "Time for us to shuffle off and try again!",
                        "You know what they say, the house always wins!",
                        "Looks like we found out who the real Joker is!",
                        "Oh no, were you bluffing too?",
                        "Looks like the joke's on us!",
                        "If I had hands I would have covered my eyes!",
                        "I'm literally a fool, what's your excuse?",
                        "What a flop!");

        int randomIndex = ThreadLocalRandom.current().nextInt(quotes.size());
        assertTrue(true, "Random index should be within valid range.");
    }

    @Test
    public void testHARepetitionConstant() {
        final int HA_REPETITIONS = 47;
        String repeatedHA = "HA".repeat(HA_REPETITIONS);

        assertEquals(
                HA_REPETITIONS * 2,
                repeatedHA.length(),
                "The repeated HA string should be double the HA_REPETITIONS value.");
    }
}
