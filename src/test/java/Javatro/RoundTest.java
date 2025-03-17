package Javatro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.util.List;

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
        assertEquals(false, round.isRoundOver());
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

    @Test
    public void round_playCards_roundNotOver() throws JavatroException {
        State state = new State(100, 3, new Deck());
        Round round = new Round(state);
        round.playCards(List.of(0, 1, 2, 3, 4));
        assertEquals(false, round.isRoundOver());
        assertEquals(false, round.isWon());

        state = new State(1000, 3, new Deck());
        round = new Round(state);
        round.playCards(List.of(0, 1, 2, 3, 4));
        assertEquals(false, round.isRoundOver());
        assertEquals(false, round.isWon());

        state = new State(100, 3000, new Deck());
        round = new Round(state);
        for (int i = 0; i < 5; i++) {
            round.playCards(List.of(0, 1, 2, 3, 4));
        }
        assertEquals(false, round.isRoundOver());
        assertEquals(false, round.isWon());
    }

    private void assertRoundOverAfterPlays(
            int blindScore,
            int totalPlays,
            int playsToMake,
            boolean expectedIsOver,
            boolean expectedIsWon)
            throws JavatroException {
        State state = new State(blindScore, totalPlays, new Deck());
        Round round = new Round(state);

        for (int i = 0; i < playsToMake; i++) {
            round.playCards(List.of(0, 1, 2, 3, 4));
        }

        assertEquals(expectedIsOver, round.isRoundOver());
        if (expectedIsWon) {
            assertEquals(expectedIsWon, round.isWon());
        }
    }

    @Test
    public void round_playCards_roundOver() throws JavatroException {
        // Round is over after using all plays
        assertRoundOverAfterPlays(99999, 3, 3, true, false);
        assertRoundOverAfterPlays(99999, 5, 5, true, false);
        assertRoundOverAfterPlays(99999, 8, 8, true, false);

        // Round is over and won when blind score is 0
        assertRoundOverAfterPlays(0, 1, 1, true, true);
    }

    private void assertPlayCardsFails(
            int blindScore, int remainingPlays, int playsToMake, String expectedErrorMessage)
            throws JavatroException {

        State state = new State(blindScore, remainingPlays, new Deck());
        try {
            Round round = new Round(state);

            // Make the specified number of valid plays
            for (int i = 0; i < playsToMake; i++) {
                round.playCards(List.of(0, 1, 2, 3, 4));
            }

            // Attempt one more play which should fail

            round.playCards(List.of(0, 1, 2, 3, 4));
            fail();
        } catch (JavatroException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void round_playCards_tooManyPlays() throws JavatroException {
        // Test with 3 plays
        assertPlayCardsFails(100, 3, 3, "No plays remaining");

        // Test with 5 plays
        assertPlayCardsFails(100, 5, 5, "No plays remaining");

        // Test with 0 plays
        assertPlayCardsFails(100, 0, 0, "Number of plays per round must be greater than 0");
    }

    private void assertPlayCardsInvalidHandSize(
            int blindScore,
            int remainingPlays,
            List<Integer> cardIndices,
            String expectedErrorMessage)
            throws JavatroException {
        State state = new State(blindScore, remainingPlays, new Deck());
        Round round = new Round(state);

        try {
            round.playCards(cardIndices);
            fail();
        } catch (JavatroException e) {
            assertEquals(expectedErrorMessage, e.getMessage());
        }
    }

    @Test
    public void round_playCards_invalidHandSize() throws JavatroException {
        assertPlayCardsInvalidHandSize(
                100, 3, List.of(0, 1, 2, 3, 4, 5), "Must play exactly 5 cards");
        assertPlayCardsInvalidHandSize(100, 3, List.of(0, 1, 2, 3), "Must play exactly 5 cards");
        assertPlayCardsInvalidHandSize(
                100, 3, List.of(0, 1, 2, 3, 4, 5, 6), "Must play exactly 5 cards");
        assertPlayCardsInvalidHandSize(
                100, 3, List.of(0, 1, 2, 3, 4, 5, 6, 7), "Must play exactly 5 cards");
        // Test with 0 cards
        assertPlayCardsInvalidHandSize(100, 3, List.of(), "Must play exactly 5 cards");
    }
}
