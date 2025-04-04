package javatro.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Comprehensive test suite for {@link PokerHand} class functionality.
 * Tests focus on core business logic while making following assumptions:
 * 1. {@link PlanetCard} returns level 1 for all new hand types
 * 2. {@link JavatroCore} maintains play counts correctly across increments
 * 3. Chip/multiplier increments are handled by external systems as per base values
 */
class PokerHandTest {
    private PokerHand highCardHand;
    private PokerHand flushFiveHand;

    @BeforeEach
    void setUp() {
        highCardHand = new PokerHand(PokerHand.HandType.HIGH_CARD);
        flushFiveHand = new PokerHand(PokerHand.HandType.FLUSH_FIVE);
    }

    /**
     * Tests baseline chip calculation without level progression.
     * Assumes PlanetCard returns level 1 for new hands.
     */
    @Test
    void getChips_shouldReturnBaseValueAtLevelOne() {
        assertEquals(5, highCardHand.getChips(), "High Card base chips mismatch");
        assertEquals(160, flushFiveHand.getChips(), "Flush Five base chips mismatch");
    }

    /**
     * Tests multiplier calculation without level progression.
     * Assumes PlanetCard returns level 1 for new hands.
     */
    @Test
    void getMultiplier_shouldReturnBaseValueAtLevelOne() {
        assertEquals(1, highCardHand.getMultiplier(), "High Card base multiplier mismatch");
        assertEquals(16, flushFiveHand.getMultiplier(), "Flush Five base multiplier mismatch");
    }

    /**
     * Tests hand name retrieval matches enum declaration.
     */
    @Test
    void getHandName_shouldReturnCorrectName() {
        assertEquals("High Card", highCardHand.getHandName(), "Hand name mismatch");
        assertEquals("Flush Five", flushFiveHand.getHandName(), "Hand name mismatch");
    }

    /**
     * Tests play counter initialization and increment sequence.
     * Assumes JavatroCore maintains separate counts per hand type.
     */
    @Test
    void playCount_shouldIncrementSeparatelyPerHandType() {
        final int initialHighCardPlays = highCardHand.getPlayCount();
        final int initialFlushPlays = flushFiveHand.getPlayCount();

        highCardHand.incrementPlayed();
        highCardHand.incrementPlayed();

        assertEquals(initialHighCardPlays + 2, highCardHand.getPlayCount(),
                "High Card play count mismatch");
        assertEquals(initialFlushPlays, flushFiveHand.getPlayCount(),
                "Flush Five play count should remain unchanged");
    }

    /**
     * Tests that incrementPlayed() maintains instance identity.
     * Since state is managed externally, same instance should be returned.
     */
    @Test
    void incrementPlayed_shouldReturnSameInstance() {
        PokerHand original = highCardHand;
        PokerHand updated = original.incrementPlayed();
        assertSame(original, updated, "Should return same instance after increment");
    }

    /**
     * Tests string representation contains all critical components.
     * Format: "Hand Name (Level: X, Chips: Y, Multiplier: Z, Played: W)"
     */
    @Test
    void toString_shouldContainAllComponents() {
        String result = highCardHand.toString();

        assertAll("All components should be present",
                () -> assertTrue(result.contains("High Card"), "Missing hand name"),
                () -> assertTrue(result.contains("Level:"), "Missing level"),
                () -> assertTrue(result.contains("Chips:"), "Missing chips"),
                () -> assertTrue(result.contains("Multiplier:"), "Missing multiplier"),
                () -> assertTrue(result.contains("Played:"), "Missing play count")
        );
    }

    /**
     * Tests all enum values have non-empty hand names.
     */
    @Test
    void handTypeEnum_shouldHaveValidNames() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            assertNotNull(handType.getHandName(), "Null hand name for " + handType);
            assertFalse(handType.getHandName().trim().isEmpty(),
                    "Empty hand name for " + handType);
        }
    }

    /**
     * Tests base chip values follow expected hierarchy.
     */
    @Test
    void handTypeChips_shouldMaintainValueHierarchy() {
        assertTrue(PokerHand.HandType.FLUSH_FIVE.getChips() >
                        PokerHand.HandType.FLUSH_HOUSE.getChips(),
                "Flush Five should have higher chips than Flush House");

        assertTrue(PokerHand.HandType.HIGH_CARD.getChips() <
                        PokerHand.HandType.PAIR.getChips(),
                "Pair should have higher chips than High Card");
    }

    /**
     * Tests multiplier values are positive for all hand types.
     */
    @Test
    void handTypeMultipliers_shouldBePositive() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            assertTrue(handType.getMultiplier() > 0,
                    "Non-positive multiplier for " + handType.name());
        }
    }
}