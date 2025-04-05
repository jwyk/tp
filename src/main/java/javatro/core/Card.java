// @@author Markneoneo
package javatro.core;

/**
 * Represents a playing card with a rank and a suit in a card game. Each card has a specific rank
 * (e.g., ACE, KING) and suit (e.g., HEARTS, SPADES). The card's rank determines both its display
 * symbol and its chip value in gameplay.
 */
public record Card(Rank rank, Suit suit) {

    /** Compact constructor to validate card components. */
    public Card {
        assert rank != null : "Rank must not be null";
        assert suit != null : "Suit must not be null";
    }

    /**
     * Constructs a new card by copying an existing card instance.
     *
     * @param other The card to copy. Must not be {@code null}.
     */
    public Card(Card other) {
        this(other.rank(), other.suit());
    }

    /**
     * Enumeration of card ranks with associated symbols and chip values. Chip values follow
     * standard casino blackjack values.
     */
    public enum Rank {
        TWO("2", 2),
        THREE("3", 3),
        FOUR("4", 4),
        FIVE("5", 5),
        SIX("6", 6),
        SEVEN("7", 7),
        EIGHT("8", 8),
        NINE("9", 9),
        TEN("10", 10),
        JACK("J", 10),
        QUEEN("Q", 10),
        KING("K", 10),
        ACE("A", 11);

        private final String symbol;
        private final int chips;

        /**
         * Creates a rank with its display symbol and chip value.
         *
         * @param symbol The character representation of the rank
         * @param chips The game value in chips for this rank
         */
        Rank(String symbol, int chips) {
            assert symbol != null : "Rank symbol cannot be null";
            this.symbol = symbol;
            this.chips = chips;
        }

        /** Returns the display symbol for this rank. */
        public String getSymbol() {
            return symbol;
        }

        /** Returns the chip value used in game calculations. */
        public int getChips() {
            return chips;
        }
    }

    /** Enumeration of card suits with full display names. */
    public enum Suit {
        HEARTS("Hearts"),
        CLUBS("Clubs"),
        SPADES("Spades"),
        DIAMONDS("Diamonds");

        private final String name;

        /**
         * Creates a suit with its full display name.
         *
         * @param name The formal name of the suit
         */
        Suit(String name) {
            assert name != null : "Suit name cannot be null";
            this.name = name;
        }

        /** Returns the formal name of the suit. */
        public String getName() {
            return name;
        }
    }

    /**
     * Returns the chip value of this card based on its rank.
     *
     * @return Chip value for game calculations
     */
    public int getChips() {
        return rank.getChips();
    }

    /**
     * Returns a human-readable string representation of the card. Format: "[Symbol] of [Suit]"
     * (e.g., "A of Hearts").
     */
    @Override
    public String toString() {
        // Format: <Rank Symbol> of <Suit Name>
        return String.format("%s of %s", rank.getSymbol(), suit.getName());
    }
}
