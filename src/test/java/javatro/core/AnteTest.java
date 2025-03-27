package javatro.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Ante class. This class tests the functionality of the Ante class,
 * including the initialization of values, progression between rounds, blind multipliers,
 * and score calculations.
 */
public class AnteTest {
    private Ante ante;

    /**
     * Initializes a new Ante object before each test.
     */
    @BeforeEach
    void setUp() {
        ante = new Ante();
    }

    /**
     * Tests the initial values of the Ante object.
     * Ensures the Ante count starts at 1, the blind is set to SMALL_BLIND,
     * and the round score is set to 300.
     */
    @Test
    void testInitialValues() {
        assertEquals(1, ante.getAnteCount(), "Ante count should start at 1");
        assertEquals(Ante.Blind.SMALL_BLIND, ante.getBlind(), "Initial blind should be SMALL_BLIND");
        assertEquals(300, ante.getRoundScore(), "Initial round score should be 300");
    }

    /**
     * Tests the multipliers for different blinds.
     * Verifies that each blind (SMALL_BLIND, LARGE_BLIND, BOSS_BLIND) has the expected multiplier.
     */
    @Test
    void testBlindMultiplier() {
        ante.setBlind(Ante.Blind.SMALL_BLIND);
        assertEquals(1.0, ante.getBlind().getMultiplier(), "SMALL_BLIND should have a multiplier of 1.0");

        ante.setBlind(Ante.Blind.LARGE_BLIND);
        assertEquals(1.5, ante.getBlind().getMultiplier(), "LARGE_BLIND should have a multiplier of 1.5");

        ante.setBlind(Ante.Blind.BOSS_BLIND);
        assertEquals(2.0, ante.getBlind().getMultiplier(), "BOSS_BLIND should have a multiplier of 2.0");
    }

    /**
     * Tests the progression of the blind and ante count across rounds.
     * Verifies that after each round, the blind changes in the expected order,
     * and the ante count increments correctly, resetting after reaching a cap.
     */
    @Test
    void testNextRoundProgression() {
        ante.nextRound();
        assertEquals(Ante.Blind.LARGE_BLIND, ante.getBlind(), "Blind should change to LARGE_BLIND");

        ante.nextRound();
        assertEquals(Ante.Blind.BOSS_BLIND, ante.getBlind(), "Blind should change to BOSS_BLIND");

        ante.nextRound();
        assertEquals(2, ante.getAnteCount(), "Ante count should increment");
        assertEquals(Ante.Blind.SMALL_BLIND, ante.getBlind(), "Blind should reset to SMALL_BLIND");
    }

    /**
     * Tests the calculation of the round score based on the current blind.
     * Verifies that the score is calculated correctly for each blind.
     */
    @Test
    void testRoundScoreCalculation() {
        ante.setBlind(Ante.Blind.SMALL_BLIND);
        assertEquals(300, ante.getRoundScore(), "Score for SMALL_BLIND should be correct");

        ante.setBlind(Ante.Blind.LARGE_BLIND);
        assertEquals(450, ante.getRoundScore(), "Score for LARGE_BLIND should be correct");

        ante.setBlind(Ante.Blind.BOSS_BLIND);
        assertEquals(600, ante.getRoundScore(), "Score for BOSS_BLIND should be correct");
    }

    /**
     * Tests the maximum ante count cap.
     * Ensures that the ante count does not exceed 8 after several rounds.
     */
    @Test
    void testMaxAnteCap() {
        for (int i = 0; i < 8; i++) {
            ante.nextRound();
            ante.nextRound();
            ante.nextRound();
        }
        assertEquals(8, ante.getAnteCount(), "Ante count should not exceed 8");
    }

    /**
     * Tests the reset functionality for the ante.
     * Verifies that the ante count and blind reset to their initial values after calling resetAnte().
     */
    @Test
    void testResetAnte() {
        ante.nextRound();
        ante.nextRound();
        ante.nextRound();
        ante.resetAnte();
        assertEquals(1, ante.getAnteCount(), "Ante count should reset to 1");
        assertEquals(Ante.Blind.SMALL_BLIND, ante.getBlind(), "Blind should reset to SMALL_BLIND");
    }
}
