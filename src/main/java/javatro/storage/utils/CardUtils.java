package javatro.storage.utils;

import javatro.core.Ante;
import javatro.core.Card;
import javatro.core.Deck;
import javatro.core.jokers.Joker;
import javatro.core.jokers.addchip.OddToddJoker;
import javatro.core.jokers.addchip.ScaryFaceJoker;
import javatro.core.jokers.addmult.*;
import javatro.display.DeckArt;

import static javatro.core.Ante.Blind.BOSS_BLIND;
import static javatro.core.Ante.Blind.LARGE_BLIND;
import static javatro.core.Ante.Blind.SMALL_BLIND;
import static javatro.display.DeckArt.ABANDONED_DECK;
import static javatro.display.DeckArt.BLUE_DECK;
import static javatro.display.DeckArt.CHECKERED_DECK;
import static javatro.display.DeckArt.RED_DECK;

public class CardUtils {

    public static DeckArt fromStorageKey(String key) {
        return switch (key.toUpperCase()) {
            case "RED" -> RED_DECK;
            case "BLUE" -> BLUE_DECK;
            case "CHECKERED" -> CHECKERED_DECK;
            case "ABANDONED" -> ABANDONED_DECK;
            default -> throw new IllegalArgumentException("Unknown deck art: " + key);
        };
    }

    public static Deck.DeckType DeckFromKey(String key) {
        return switch (key.toUpperCase()) {
            case "RED" -> Deck.DeckType.RED;
            case "BLUE" -> Deck.DeckType.BLUE;
            case "CHECKERED" -> Deck.DeckType.CHECKERED;
            case "ABANDONED" -> Deck.DeckType.ABANDONED;
            default -> throw new IllegalArgumentException("Unknown deck type: " + key);
        };
    }

    public static Ante.Blind BlindFromKey(String key) {
        return switch (key.toUpperCase()) {
            case "SMALL BLIND" -> SMALL_BLIND;
            case "LARGE BLIND" -> LARGE_BLIND;
            case "BOSS BLIND" -> BOSS_BLIND;
            default -> throw new IllegalArgumentException("Unknown blind type: " + key);
        };
    }

    public static Card parseCardString(String cardString) {
        // Ensure the string is not null or empty
        if (cardString == null || cardString.length() < 2) {
            throw new IllegalArgumentException("Invalid card string");
        }

        // Extract the rank and suit from the string
        String rankStr =
                cardString.substring(0, cardString.length() - 1); // All but the last character
        char suitChar = cardString.charAt(cardString.length() - 1); // Last character

        // Parse the rank
        Card.Rank rank =
                switch (rankStr) {
                    case "2" -> Card.Rank.TWO;
                    case "3" -> Card.Rank.THREE;
                    case "4" -> Card.Rank.FOUR;
                    case "5" -> Card.Rank.FIVE;
                    case "6" -> Card.Rank.SIX;
                    case "7" -> Card.Rank.SEVEN;
                    case "8" -> Card.Rank.EIGHT;
                    case "9" -> Card.Rank.NINE;
                    case "10" -> Card.Rank.TEN;
                    case "J" -> Card.Rank.JACK;
                    case "Q" -> Card.Rank.QUEEN;
                    case "K" -> Card.Rank.KING;
                    case "A" -> Card.Rank.ACE;
                    default -> throw new IllegalArgumentException("Invalid rank: " + rankStr);
                };

        // Parse the suit
        Card.Suit suit =
                switch (Character.toUpperCase(suitChar)) {
                    case 'H' -> Card.Suit.HEARTS;
                    case 'C' -> Card.Suit.CLUBS;
                    case 'S' -> Card.Suit.SPADES;
                    case 'D' -> Card.Suit.DIAMONDS;
                    default -> throw new IllegalArgumentException("Invalid suit: " + suitChar);
                };

        // Return the constructed Card
        return new Card(rank, suit);
    }

    public static String cardToString(Card card) {
        // Get the rank and suit from the card
        String rankStr = card.rank().getSymbol(); // Get the symbol (e.g., "A", "K", "10")
        String suitStr =
                switch (card.suit()) {
                    case HEARTS -> "H";
                    case CLUBS -> "C";
                    case SPADES -> "S";
                    case DIAMONDS -> "D";
                };

        // Combine rank and suit to form the card string
        return rankStr + suitStr;
    }

    public static boolean isValidCardString(String cardString) {
        if (cardString == null || cardString.length() < 2) {
            return false; // Null or too short to be a valid card
        }

        // Extract the rank and suit from the string
        String rankStr =
                cardString.substring(0, cardString.length() - 1); // All but the last character
        char suitChar = cardString.charAt(cardString.length() - 1); // Last character

        // Check if the rank is valid
        boolean isValidRank =
                switch (rankStr) {
                    case "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" -> true;
                    default -> false;
                };

        // Check if the suit is valid
        boolean isValidSuit =
                switch (Character.toUpperCase(suitChar)) {
                    case 'H', 'C', 'S', 'D' -> true;
                    default -> false;
                };

        // Return true only if both rank and suit are valid
        return isValidRank && isValidSuit;
    }

    public static Joker parseJokerString(String jokerName) {
        return switch (jokerName.toUpperCase()) {
            case "ODDTODDJOKER" -> new OddToddJoker();
            case "SCARYFACEJOKER" -> new ScaryFaceJoker();
            case "ABSTRACTJOKER" -> new AbstractJoker();
            case "GLUTTONOUSJOKER" -> new GluttonousJoker();
            case "GREEDYJOKER" -> new GreedyJoker();
            case "HALFJOKER" -> new HalfJoker();
            case "LUSTYJOKER" -> new LustyJoker();
            case "WRATHFULJOKER" -> new WrathfulJoker();
            default -> throw new IllegalArgumentException("Invalid Joker name: " + jokerName);
        };
    }

    public static String jokerToString(Joker joker) {
        if (joker == null) {
            throw new IllegalArgumentException("Joker cannot be null");
        }
        return joker.getIdentifierName(); // Returns the class name of the Joker
    }

    public static boolean isValidJokerString(String jokerName) {
        if (jokerName == null || jokerName.trim().isEmpty()) {
            return false; // Null or empty string is not valid
        }

        return switch (jokerName.toUpperCase()) {
            case "ODDTODDJOKER",
                    "SCARYFACEJOKER",
                    "ABSTRACTJOKER",
                    "GLUTTONOUSJOKER",
                    "GREEDYJOKER",
                    "HALFJOKER",
                    "LUSTYJOKER",
                    "WRATHFULJOKER" -> true;
            default -> false;
        };
    }
}
