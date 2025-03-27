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

    @Test
    void testInitialValues() {
        assertEquals(1, ante.getAnteCount(), "Ante count should start at 1");
        assertEquals(Ante.Blind.SMALL_BLIND, ante.getBlind(), "Initial blind should be SMALL_BLIND");
        assertEquals(300, ante.getRoundScore(), "Initial round score should be 300");
    }

    @Test
    void testBlindMultiplier() {
        ante.setBlind(Ante.Blind.SMALL_BLIND);
        assertEquals(1.0, ante.getBlind().getMultiplier(), "SMALL_BLIND should have a multiplier of 1.0");

        ante.setBlind(Ante.Blind.LARGE_BLIND);
        assertEquals(1.5, ante.getBlind().getMultiplier(), "LARGE_BLIND should have a multiplier of 1.5");

        ante.setBlind(Ante.Blind.BOSS_BLIND);
        assertEquals(2.0, ante.getBlind().getMultiplier(), "BOSS_BLIND should have a multiplier of 2.0");
    }

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

    @Test
    void testRoundScoreCalculation() {
        ante.setBlind(Ante.Blind.SMALL_BLIND);
        assertEquals(300, ante.getRoundScore(), "Score for SMALL_BLIND should be correct");

        ante.setBlind(Ante.Blind.LARGE_BLIND);
        assertEquals(450, ante.getRoundScore(), "Score for LARGE_BLIND should be correct");

        ante.setBlind(Ante.Blind.BOSS_BLIND);
        assertEquals(600, ante.getRoundScore(), "Score for BOSS_BLIND should be correct");
    }

    @Test
    void testMaxAnteCap() {
        for (int i = 0; i < 8; i++) {
            ante.nextRound();
            ante.nextRound();
            ante.nextRound();
        }
        assertEquals(8, ante.getAnteCount(), "Ante count should not exceed 8");
    }

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
