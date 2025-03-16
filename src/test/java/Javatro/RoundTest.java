package Javatro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class RoundTest {

    private void assertRoundInitialization(
            int blindScore, int remainingPlays, int currentScore, int remainingDiscards)
            throws JavatroException {
        State state = new State(blindScore, remainingPlays, new Deck());
        Round round = new Round(state);
        assertEquals(blindScore, round.getBlindScore());
        assertEquals(remainingPlays, round.getRemainingPlays());
        assertEquals(currentScore, round.getCurrentScore());
        assertEquals(remainingDiscards, round.getRemainingDiscards());
    }

    private void assertRoundInitializationFailure(
            int blindScore, int remainingPlays, Deck deck, String expectedMessage) {
        State state = new State(blindScore, remainingPlays, deck);
        try {
            new Round(state);
            fail();
        } catch (JavatroException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void round_correctInitialization_success() throws JavatroException {
        assertRoundInitialization(100, 3, 0, 3);
        assertRoundInitialization(200, 5, 0, 3);
        assertRoundInitialization(300, 7, 0, 3);
        assertRoundInitialization(0, 1, 0, 3);
    }

    @Test
    public void round_incorrectInitializatioin() throws JavatroException {
        assertRoundInitializationFailure(
                100, 0, new Deck(), "Number of plays per round must be greater than 0");
        assertRoundInitializationFailure(
                -100, 3, new Deck(), "Blind score must be greater than or equal to 0");
        assertRoundInitializationFailure(100, 3, null, "Deck cannot be null");
        assertRoundInitializationFailure(
                -100, 0, new Deck(), "Blind score must be greater than or equal to 0");
        assertRoundInitializationFailure(
                -100, 3, null, "Blind score must be greater than or equal to 0");
        assertRoundInitializationFailure(
                100, 0, null, "Number of plays per round must be greater than 0");
        assertRoundInitializationFailure(
                -100, 0, null, "Blind score must be greater than or equal to 0");
    }
}
