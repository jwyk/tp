// @@author Markneoneo
package javatro.core;

/**
 * Represents the evaluated result of a poker hand with tracking for progression levels and usage counts.
 * The hand's value is determined by its type, which includes base chips and multipliers adjusted by level.
 * Play counts are managed externally by {@link JavatroCore}.
 *
 * @param handType The classification of the poker hand determining base values and increments
 */
public record PokerHand(HandType handType) {

    /**
     * Calculates the total chip value adjusted by the hand's current progression level.
     * Formula: base_chips + (current_level - 1) * level_increment
     *
     * @return Total chips adjusted for current level
     */
    public int getChips() {
        int base = handType.getChips();
        int level = PlanetCard.getLevel(handType);
        assert level >= 1 : "Level must be ≥ 1";
        int chipIncrement = PlanetCard.getChipIncrement(handType);

        return base + (level - 1) * chipIncrement;
    }

    /**
     * Calculates the multiplier value adjusted by the hand's current progression level.
     * Formula: base_multiplier + (current_level - 1) * multiplier_increment
     *
     * @return Total multiplier adjusted for current level
     */
    public int getMultiplier() {
        int base = handType.getMultiplier();
        int level = PlanetCard.getLevel(handType);
        assert level >= 1 : "Level must be ≥ 1";
        int multiIncrement = PlanetCard.getMultiIncrement(handType);

        return base + (level - 1) * multiIncrement;
    }

    /**
     * Gets the display name of the poker hand type.
     *
     * @return Formal name of the hand type
     */
    public String getHandName() {
        return handType.getHandName();
    }

    /**
     * Retrieves the number of times this hand type has been played.
     *
     * @return Current play count from external tracking system
     */
    public int getPlayCount() {
        return JavatroCore.getPlayCount(handType);
    }

    /**
     * Increments the play counter for this hand type in the external tracker.
     * Returns the same instance since state is managed externally.
     *
     * @return Current instance after updating play count
     */
    public PokerHand incrementPlayed() {
        JavatroCore.incrementPlayCount(handType);
        return this;
    }

    @Override
    public String toString() {
        // Includes level-adjusted chips but base multiplier (original design choice)
        return String.format(
                "%s (Level: %d, Chips: %d, Multiplier: %d, Played: %d)",
                handType.getHandName(),
                PlanetCard.getLevel(handType),
                getChips(),
                handType.getMultiplier(),  // Base multiplier per original implementation
                getPlayCount()
        );
    }

    /**
     * Enumeration of poker hand types with associated base values and progression parameters.
     * Each hand type has:
     * - Display name
     * - Base chip value
     * - Base multiplier value
     * Level increments are managed externally by {@link PlanetCard}.
     */
    public enum HandType {
        // Standard poker hands
        FLUSH_FIVE("Flush Five", 160, 16),
        FLUSH_HOUSE("Flush House", 140, 14),
        FIVE_OF_A_KIND("Five of a Kind", 120, 12),
        ROYAL_FLUSH("Royal Flush", 100, 8),
        STRAIGHT_FLUSH("Straight Flush", 100, 8),
        FOUR_OF_A_KIND("Four of a Kind", 60, 7),
        FULL_HOUSE("Full House", 40, 4),
        FLUSH("Flush", 35, 4),
        STRAIGHT("Straight", 30, 4),
        THREE_OF_A_KIND("Three of a Kind", 30, 3),
        TWO_PAIR("Two Pair", 20, 2),
        PAIR("Pair", 10, 2),
        HIGH_CARD("High Card", 5, 1);

        private final String handName;
        private final int chips;
        private final int multiplier;

        /**
         * Creates a hand type specification.
         *
         * @param handName   Display name
         * @param chips      Base chip value
         * @param multiplier Base multiplier value
         */
        HandType(String handName, int chips, int multiplier) {
            this.handName = handName;
            this.chips = chips;
            this.multiplier = multiplier;
        }

        /**
         * Gets the display name of the hand type.
         *
         * @return Formal hand name
         */
        public String getHandName() {
            return handName;
        }

        /**
         * Gets the base chip value before level adjustments.
         *
         * @return Base chips for this hand type
         */
        public int getChips() {
            return chips;
        }

        /**
         * Gets the base multiplier value before level adjustments.
         *
         * @return Base multiplier for this hand type
         */
        public int getMultiplier() {
            return multiplier;
        }
    }
}