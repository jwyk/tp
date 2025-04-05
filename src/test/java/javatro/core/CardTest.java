// @@author Markneoneo
package javatro.core;

import static javatro.core.Card.Rank.ACE;
import static javatro.core.Card.Rank.EIGHT;
import static javatro.core.Card.Rank.FIVE;
import static javatro.core.Card.Rank.FOUR;
import static javatro.core.Card.Rank.JACK;
import static javatro.core.Card.Rank.KING;
import static javatro.core.Card.Rank.NINE;
import static javatro.core.Card.Rank.QUEEN;
import static javatro.core.Card.Rank.SEVEN;
import static javatro.core.Card.Rank.SIX;
import static javatro.core.Card.Rank.TEN;
import static javatro.core.Card.Rank.THREE;
import static javatro.core.Card.Rank.TWO;
import static javatro.core.Card.Suit.CLUBS;
import static javatro.core.Card.Suit.DIAMONDS;
import static javatro.core.Card.Suit.HEARTS;
import static javatro.core.Card.Suit.SPADES;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for the {@link Card} class and its associated enums. Verifies core
 * functionality, edge cases, and expected behaviors without using parameterized tests.
 *
 * <p>Test coverage includes:
 *
 * <ul>
 *   <li>All 52 card combinations (13 ranks × 4 suits)
 *   <li>Chip value calculations for all ranks
 *   <li>String representation formatting
 *   <li>Equality and hash code contracts
 *   <li>Enum completeness and values
 *   <li>Record component integrity
 * </ul>
 */
class CardTest {

    /**
     * Tests creation of all possible card combinations. Iterates through every rank and suit
     * combination to verify:
     *
     * <ul>
     *   <li>Successful card instantiation
     *   <li>Correct rank component storage
     *   <li>Correct suit component storage
     * </ul>
     */
    @Test
    @DisplayName("Test all card combinations")
    void testAllCardCombinations() {
        // Nested loops test all 13 ranks × 4 suits = 52 combinations
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                Card card = new Card(rank, suit);
                assertNotNull(card, "Card should not be null");
                assertEquals(rank, card.rank(), "Rank should match constructor argument");
                assertEquals(suit, card.suit(), "Suit should match constructor argument");
            }
        }
    }

    /**
     * Verifies chip values for all card ranks. Tests both numerical ranks and special values for
     * face cards:
     *
     * <ul>
     *   <li>Numerical ranks (2-10) have matching chip values
     *   <li>Face cards (JACK, QUEEN, KING) have 10 chips
     *   <li>ACE has special value of 11 chips
     * </ul>
     */
    @Test
    @DisplayName("Test rank chip values")
    void testRankChipValues() {
        // Test numerical ranks
        assertEquals(2, TWO.getChips(), "TWO should have 2 chips");
        assertEquals(3, THREE.getChips(), "THREE should have 3 chips");
        assertEquals(4, FOUR.getChips(), "FOUR should have 4 chips");
        assertEquals(5, FIVE.getChips(), "FIVE should have 5 chips");
        assertEquals(6, SIX.getChips(), "SIX should have 6 chips");
        assertEquals(7, SEVEN.getChips(), "SEVEN should have 7 chips");
        assertEquals(8, EIGHT.getChips(), "EIGHT should have 8 chips");
        assertEquals(9, NINE.getChips(), "NINE should have 9 chips");
        assertEquals(10, TEN.getChips(), "TEN should have 10 chips");

        // Test face cards
        assertEquals(10, JACK.getChips(), "JACK should have 10 chips");
        assertEquals(10, QUEEN.getChips(), "QUEEN should have 10 chips");
        assertEquals(10, KING.getChips(), "KING should have 10 chips");

        // Test special case
        assertEquals(11, ACE.getChips(), "ACE should have 11 chips");
    }

    /**
     * Validates symbol representations for all card ranks. Ensures proper symbols for both
     * numerical and face cards:
     *
     * <ul>
     *   <li>Numerical ranks show their number
     *   <li>Face cards use single-letter abbreviations
     *   <li>ACE uses 'A' symbol
     * </ul>
     */
    @Test
    @DisplayName("Test rank symbols")
    void testRankSymbols() {
        // Verify numerical symbols
        assertEquals("2", TWO.getSymbol(), "TWO symbol mismatch");
        assertEquals("3", THREE.getSymbol(), "THREE symbol mismatch");
        assertEquals("4", FOUR.getSymbol(), "FOUR symbol mismatch");
        assertEquals("5", FIVE.getSymbol(), "FIVE symbol mismatch");
        assertEquals("6", SIX.getSymbol(), "SIX symbol mismatch");
        assertEquals("7", SEVEN.getSymbol(), "SEVEN symbol mismatch");
        assertEquals("8", EIGHT.getSymbol(), "EIGHT symbol mismatch");
        assertEquals("9", NINE.getSymbol(), "NINE symbol mismatch");
        assertEquals("10", TEN.getSymbol(), "TEN symbol mismatch");

        // Verify face card symbols
        assertEquals("J", JACK.getSymbol(), "JACK symbol mismatch");
        assertEquals("Q", QUEEN.getSymbol(), "QUEEN symbol mismatch");
        assertEquals("K", KING.getSymbol(), "KING symbol mismatch");
        assertEquals("A", ACE.getSymbol(), "ACE symbol mismatch");
    }

    /**
     * Tests formal names for all suit enums. Verifies proper capitalization and spelling:
     *
     * <ul>
     *   <li>First letter capitalized
     *   <li>Subsequent letters lowercase
     *   <li>Full English names
     * </ul>
     */
    @Test
    @DisplayName("Test suit names")
    void testSuitNames() {
        assertEquals("Hearts", HEARTS.getName(), "HEARTS name mismatch");
        assertEquals("Clubs", CLUBS.getName(), "CLUBS name mismatch");
        assertEquals("Spades", SPADES.getName(), "SPADES name mismatch");
        assertEquals("Diamonds", DIAMONDS.getName(), "DIAMONDS name mismatch");
    }

    /**
     * Verifies the string representation format. Tests the pattern: "[Rank Symbol] of [Suit Name]".
     * Samples include:
     *
     * <ul>
     *   <li>Numerical card with simple suit
     *   <li>Face card with different suit
     *   <li>Special case (ACE)
     * </ul>
     */
    @Test
    @DisplayName("Test string representation")
    void testToString() {
        assertEquals(
                "2 of Hearts", new Card(TWO, HEARTS).toString(), "TWO of HEARTS string mismatch");
        assertEquals(
                "A of Spades", new Card(ACE, SPADES).toString(), "ACE of SPADES string mismatch");
        assertEquals(
                "J of Diamonds",
                new Card(JACK, DIAMONDS).toString(),
                "JACK of DIAMONDS string mismatch");
        assertEquals(
                "Q of Clubs", new Card(QUEEN, CLUBS).toString(), "QUEEN of CLUBS string mismatch");
    }

    /**
     * Tests the equality contract and hash code consistency. Verifies:
     *
     * <ul>
     *   <li>Equal cards return true for equals()
     *   <li>Equal cards have matching hash codes
     *   <li>Different cards return false for equals()
     * </ul>
     */
    @Test
    @DisplayName("Test equality and hash code")
    void testEqualityAndHashCode() {
        Card card1 = new Card(TWO, CLUBS);
        Card card2 = new Card(TWO, CLUBS);
        Card card3 = new Card(KING, HEARTS);

        // Equality tests
        assertEquals(card1, card2, "Identical cards should be equal");
        assertEquals(card1.hashCode(), card2.hashCode(), "Equal cards must have equal hash codes");
        assertNotEquals(card1, card3, "Different cards should not be equal");
    }

    /**
     * Verifies proper handling of invalid comparisons. Ensures cards:
     *
     * <ul>
     *   <li>Never equal to null
     *   <li>Never equal to different types
     * </ul>
     */
    @Test
    @DisplayName("Test invalid comparisons")
    void testInvalidComparisons() {
        Card card = new Card(SEVEN, DIAMONDS);
        assertNotEquals(null, card, "Card should not equal null");
        assertNotEquals(new Object(), card, "Card should not equal other types");
    }

    /**
     * Validates completeness of enum declarations. Prevents accidental modification of enum values
     * by:
     *
     * <ul>
     *   <li>Checking count of Rank values (13)
     *   <li>Checking count of Suit values (4)
     * </ul>
     */
    @Test
    @DisplayName("Test enum completeness")
    void testEnumCompleteness() {
        assertEquals(13, Card.Rank.values().length, "Should have 13 ranks (2-10, J, Q, K, A)");
        assertEquals(
                4,
                Card.Suit.values().length,
                "Should have 4 suits (Hearts, Clubs, Spades, Diamonds)");
    }

    /**
     * Tests record component accessors. Verifies the automatically generated methods:
     *
     * <ul>
     *   <li>{@link Card#rank()}
     *   <li>{@link Card#suit()}
     * </ul>
     */
    @Test
    @DisplayName("Test record components")
    void testRecordComponents() {
        Card card = new Card(NINE, SPADES);
        assertEquals(NINE, card.rank(), "Rank component mismatch");
        assertEquals(SPADES, card.suit(), "Suit component mismatch");
    }
}
