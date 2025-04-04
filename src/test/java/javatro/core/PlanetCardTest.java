package javatro.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JUnit 5 test suite for {@link PlanetCard} class functionality.
 * <p>
 * Verifies core functionality including card retrieval, level management,
 * increment calculations, and instance properties. Maintains test isolation
 * through automatic state reset after each test.
 * </p>
 */
class PlanetCardTest {

    /**
     * Resets enhancement levels to default values after each test.
     * Ensures test isolation by clearing shared state between test executions.
     */
    @AfterEach
    void resetState() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            PlanetCard.LEVELS.put(handType, 1);
        }
    }

    /**
     * Verifies that all poker hand types have associated planet cards.
     * Tests complete coverage of {@link PokerHand.HandType} enumeration.
     */
    @Test
    void getForHand_AllHandTypes_ReturnsNonNullCards() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            PlanetCard card = PlanetCard.getForHand(handType);
            assertNotNull(card, "Missing card for hand type: " + handType);
        }
    }

    /**
     * Tests initial state configuration of enhancement levels.
     * Ensures all hand types start at level 1 before any modifications.
     */
    @Test
    void getLevel_InitialState_ReturnsBaseLevelOne() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            assertEquals(1, PlanetCard.getLevel(handType),
                    "Incorrect initial level for: " + handType);
        }
    }

    /**
     * Verifies single level enhancement functionality.
     * Checks both direct level access and subsequent retrieval.
     */
    @Test
    void apply_SingleApplication_IncrementsLevelByOne() {
        PlanetCard targetCard = PlanetCard.getForHand(PokerHand.HandType.HIGH_CARD);
        targetCard.apply();

        assertEquals(2, PlanetCard.getLevel(targetCard.getHandType()),
                "Level not incremented after single application");
    }

    /**
     * Tests multiple consecutive enhancements on the same card.
     * Verifies cumulative level increases and boundary conditions.
     */
    @Test
    void apply_MultipleApplications_IncrementsLevelAppropriately() {
        PlanetCard targetCard = PlanetCard.getForHand(PokerHand.HandType.PAIR);
        final int applications = 3;

        for (int i = 0; i < applications; i++) {
            targetCard.apply();
        }

        assertEquals(1 + applications, PlanetCard.getLevel(targetCard.getHandType()),
                "Incorrect level after multiple applications");
    }

    /**
     * Verifies isolation between different hand type enhancements.
     * Ensures modifying one card type doesn't affect others.
     */
    @Test
    void apply_OneHandType_DoesNotAffectOtherLevels() {
        PlanetCard modifiedCard = PlanetCard.getForHand(PokerHand.HandType.TWO_PAIR);
        modifiedCard.apply();

        assertEquals(2, PlanetCard.getLevel(PokerHand.HandType.TWO_PAIR),
                "Modified card level incorrect");
        assertEquals(1, PlanetCard.getLevel(PokerHand.HandType.THREE_OF_A_KIND),
                "Unrelated card level affected");
    }

    /**
     * Validates static chip increment values against known configurations.
     * Tests sample hand types to verify correct base values.
     */
    @Test
    void getChipIncrement_KnownValues_ReturnsConfiguredAmounts() {
        assertEquals(10, PlanetCard.getChipIncrement(PokerHand.HandType.HIGH_CARD),
                "Incorrect chip increment for HIGH_CARD");
        assertEquals(30, PlanetCard.getChipIncrement(PokerHand.HandType.FOUR_OF_A_KIND),
                "Incorrect chip increment for FOUR_OF_A_KIND");
        assertEquals(50, PlanetCard.getChipIncrement(PokerHand.HandType.FLUSH_FIVE),
                "Incorrect chip increment for FLUSH_FIVE");
    }

    /**
     * Validates static multiplier increments against known configurations.
     * Tests edge cases and mid-range values.
     */
    @Test
    void getMultiIncrement_KnownValues_ReturnsConfiguredAmounts() {
        assertEquals(1, PlanetCard.getMultiIncrement(PokerHand.HandType.PAIR),
                "Incorrect multi increment for PAIR");
        assertEquals(4, PlanetCard.getMultiIncrement(PokerHand.HandType.STRAIGHT_FLUSH),
                "Incorrect multi increment for STRAIGHT_FLUSH");
        assertEquals(3, PlanetCard.getMultiIncrement(PokerHand.HandType.FIVE_OF_A_KIND),
                "Incorrect multi increment for FIVE_OF_A_KIND");
    }

    /**
     * Verifies instance property consistency for a sample card.
     * Tests all getters against predefined configuration values.
     */
    @Test
    void instanceProperties_SampleCard_ReturnsConfiguredValues() {
        PlanetCard earthCard = PlanetCard.getForHand(PokerHand.HandType.FULL_HOUSE);

        assertAll("Earth card properties",
                () -> assertEquals("Earth", earthCard.getName()),
                () -> assertEquals(25, earthCard.getChipIncrement()),
                () -> assertEquals(2, earthCard.getMultiIncrement()),
                () -> assertEquals(PokerHand.HandType.FULL_HOUSE, earthCard.getHandType()),
                () -> assertEquals("planet_earth.txt", earthCard.getPath())
        );
    }

    /**
     * Ensures static and instance chip increments match for all cards.
     * Verifies API consistency between different access methods.
     */
    @Test
    void chipIncrement_StaticVsInstance_ReturnsSameValue() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            PlanetCard card = PlanetCard.getForHand(handType);
            assertEquals(PlanetCard.getChipIncrement(handType), card.getChipIncrement(),
                    "Mismatch for hand type: " + handType);
        }
    }

    /**
     * Ensures static and instance multiplier increments match for all cards.
     * Validates API consistency across different access patterns.
     */
    @Test
    void multiIncrement_StaticVsInstance_ReturnsSameValue() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            PlanetCard card = PlanetCard.getForHand(handType);
            assertEquals(PlanetCard.getMultiIncrement(handType), card.getMultiIncrement(),
                    "Mismatch for hand type: " + handType);
        }
    }
}