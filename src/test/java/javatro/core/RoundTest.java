package javatro.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import javatro.display.UI;

import java.util.List;

public class RoundTest {
    private static String getExceptionMessage(String message) {
        return UI.WARNING + UI.RED + message + UI.END;
    }

    private static final String INVALIDPLAYEDHANDERROR = "A poker hand must contain between 1 and 5 cards.";
    private static final String INVALIDPLAYSPERROUND = "Number of plays per round must be greater than 0.";
    private static final String INVALIDDECK = "Deck cannot be null.";
    private static final String INVALIDPLAYSREMAINING = "No plays remaining.";

    private void assertRoundInitialization(
            javatro.core.AnteBase ante, javatro.core.BlindType blind, int remainingPlays)
            throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(ante, blind, remainingPlays, deck, "", "");
        int expectedBlindScore = (int) (ante.getValue() * blind.getMultiplier());
        assertEquals(expectedBlindScore, round.getBlindScore());
        assertEquals(remainingPlays, round.getRemainingPlays());
        assertEquals(0, round.getCurrentScore());
        assertEquals(4, round.getRemainingDiscards());
        assertFalse(round.isRoundOver());
    }

    private void assertRoundInitializationFailure(
            javatro.core.AnteBase ante,
            javatro.core.BlindType blind,
            int remainingPlays,
            Deck deck,
            String expectedMessage) {
        try {
            new Round(ante, blind, remainingPlays, deck, "", "");
            fail();
        } catch (JavatroException e) {
            assertEquals(getExceptionMessage(expectedMessage), e.getMessage());
        }
    }

    private void assertRoundOverAfterPlays(
            javatro.core.AnteBase ante,
            javatro.core.BlindType blind,
            int totalPlays,
            int playsToMake,
            boolean expectedIsOver)
            throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(ante, blind, totalPlays, deck, "", "");

        for (int i = 0; i < playsToMake; i++) {
            round.playCards(List.of(0, 1, 2, 3, 4));
        }

        assertEquals(expectedIsOver, round.isRoundOver());
    }

    private void assertPlayCardsFails(
            javatro.core.AnteBase ante,
            javatro.core.BlindType blind,
            int remainingPlays,
            int playsToMake,
            String expectedErrorMessage)
            throws JavatroException {
        try {
            Deck deck = new Deck();
            Round round = new Round(ante, blind, remainingPlays, deck, "", "");

            // Make the specified number of valid plays
            for (int i = 0; i < playsToMake; i++) {
                round.playCards(List.of(0, 1, 2, 3, 4));
            }

            // Attempt one more play which should fail
            round.playCards(List.of(0, 1, 2, 3, 4));
            fail();
        } catch (JavatroException e) {
            assertEquals(getExceptionMessage(expectedErrorMessage), e.getMessage());
        }
    }

    private void assertPlayCardsInvalidHandSize(
            javatro.core.AnteBase ante,
            javatro.core.BlindType blind,
            int remainingPlays,
            List<Integer> cardIndices,
            String expectedErrorMessage)
            throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(ante, blind, remainingPlays, deck, "", "");
        try {
            round.playCards(cardIndices);
            fail();
        } catch (JavatroException e) {
            assertEquals(getExceptionMessage(expectedErrorMessage), e.getMessage());
        }
    }

    private void assertRoundNotOver(
            javatro.core.AnteBase ante,
            javatro.core.BlindType blind,
            int remainingPlays,
            int playsToMake)
            throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(ante, blind, remainingPlays, deck, "", "");

        for (int i = 0; i < playsToMake; i++) {
            round.playCards(List.of(0, 1, 2, 3, 4));
        }

        assertFalse(round.isRoundOver());
        assertFalse(round.isWon());
    }

    @Test
    public void round_correctInitialization_success() throws JavatroException {
        assertRoundInitialization(javatro.core.AnteBase.ANTE_1, javatro.core.BlindType.SMALL, 3);
        assertRoundInitialization(javatro.core.AnteBase.ANTE_2, javatro.core.BlindType.SMALL, 5);
        assertRoundInitialization(javatro.core.AnteBase.ANTE_2, javatro.core.BlindType.LARGE, 7);
        assertRoundInitialization(javatro.core.AnteBase.ANTE_3, javatro.core.BlindType.BOSS, 1);
    }

    @Test
    public void round_incorrectInitializatioin() throws JavatroException {
        assertRoundInitializationFailure(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                0,
                new Deck(),
                INVALIDPLAYSPERROUND);
        assertRoundInitializationFailure(
                javatro.core.AnteBase.ANTE_1, javatro.core.BlindType.SMALL, 3, null, INVALIDDECK);
    }

    @Test
    public void round_playCards_roundNotOver() throws JavatroException {
        // Test with first blind score and plays
        assertRoundNotOver(javatro.core.AnteBase.ANTE_1, javatro.core.BlindType.SMALL, 3, 1);
        // Test with high blind score
        assertRoundNotOver(javatro.core.AnteBase.ANTE_8, javatro.core.BlindType.BOSS, 3, 1);
        // Test with many remaining plays
        assertRoundNotOver(javatro.core.AnteBase.ANTE_2, javatro.core.BlindType.LARGE, 3000, 5);
    }

    @Test
    public void round_playCards_roundOver() throws JavatroException {
        assertRoundOverAfterPlays(
                javatro.core.AnteBase.ANTE_8, javatro.core.BlindType.BOSS, 3, 3, true);
        assertRoundOverAfterPlays(
                javatro.core.AnteBase.ANTE_8, javatro.core.BlindType.BOSS, 5, 5, true);
        assertRoundOverAfterPlays(
                javatro.core.AnteBase.ANTE_8, javatro.core.BlindType.BOSS, 8, 8, true);
        assertRoundOverAfterPlays(
                javatro.core.AnteBase.ANTE_1, javatro.core.BlindType.SMALL, 1, 1, true);
    }

    @Test
    public void round_playCards_tooManyPlays() throws JavatroException {
        // Test with 3 plays
        assertPlayCardsFails(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                3,
                INVALIDPLAYSREMAINING);

        // Test with 5 plays
        assertPlayCardsFails(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                2,
                2,
                INVALIDPLAYSREMAINING);
        // Test with 0 plays
        assertPlayCardsFails(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                0,
                0,
                INVALIDPLAYSPERROUND);
    }

    @Test
    public void round_playCards_invalidHandSize() throws JavatroException {
        assertPlayCardsInvalidHandSize(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                List.of(0, 1, 2, 3, 4, 5),
                INVALIDPLAYEDHANDERROR);
        assertPlayCardsInvalidHandSize(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                List.of(0, 1, 2, 3, 4, 5, 6),
                INVALIDPLAYEDHANDERROR);
        assertPlayCardsInvalidHandSize(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                List.of(0, 1, 2, 3, 4, 5, 6, 7),
                INVALIDPLAYEDHANDERROR);
        // Test with 0 cards
        assertPlayCardsInvalidHandSize(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                List.of(),
                INVALIDPLAYEDHANDERROR);
    }

    @Test
    public void round_discardCards_success() throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                deck,
                "",
                "");

        // Initial state
        assertEquals(4, round.getRemainingDiscards());
        int initialHandSize = round.getPlayerHand().size();

        // Discard 2 cards
        round.discardCards(List.of(0, 1));

        // Check state after discard
        assertEquals(3, round.getRemainingDiscards());
        assertEquals(initialHandSize, round.getPlayerHand().size());
    }

    @Test
    public void round_discardCards_tooManyDiscards() throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                deck,
                "",
                "");

        // Use all 4 discards
        round.discardCards(List.of(0));
        round.discardCards(List.of(0));
        round.discardCards(List.of(0));
        round.discardCards(List.of(0));

        // Fifth discard should fail
        try {
            round.discardCards(List.of(0));
            fail("Should have thrown an exception for too many discards");
        } catch (JavatroException e) {
            assertEquals(getExceptionMessage("No remaining discards available"), e.getMessage());
        }
    }

    @Test
    public void round_emptyDiscardList() throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                deck,
                "",
                "");

        // Initial state
        assertEquals(4, round.getRemainingDiscards());
        int initialHandSize = round.getPlayerHand().size();

        // Discard 0 cards
        try {
            round.discardCards(List.of());
            fail("Should have thrown an exception for discarding zero cards");
        } catch (JavatroException e) {
            assertEquals(getExceptionMessage("Cannot discard zero cards"), e.getMessage());
        }

        assertEquals(4, round.getRemainingDiscards());
        assertEquals(initialHandSize, round.getPlayerHand().size());
    }

    @Test
    public void round_setNameAndDescription() throws JavatroException {
        Deck deck = new Deck();
        Round round = new Round(
                javatro.core.AnteBase.ANTE_1,
                javatro.core.BlindType.SMALL,
                3,
                deck,
                "",
                "");

        // Set new values
        round.setRoundName("New Round");
        round.setRoundDescription("New Description");

        // Check values were updated
        assertEquals("New Round", round.getRoundName());
        assertEquals("New Description", round.getRoundDescription());
    }
}
