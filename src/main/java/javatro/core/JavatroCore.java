/**
 * The {@code JavatroCore} class represents the main game model, responsible for managing game
 * rounds and initializing the game state.
 */
package javatro.core;

import javatro.core.Deck.DeckType;
import javatro.core.jokers.HeldJokers;
import javatro.core.round.Round;
import javatro.storage.Storage;

import java.util.*;

/** The core game logic class that manages the game state and rounds. */
public class JavatroCore {

    /** The current active round in the game. */
    public static Round currentRound;

    /** The current ante for the game. */
    protected static Ante ante;

    /** The current round count of the game. */
    protected static int roundCount;

    /** The number of plays give per round (Default value = 4). */
    public static int totalPlays;

    /** The deck used throughout the game. (A copy of this deck is made for every new Round) */
    public static Deck deck;

    /** The deck used throughout the game. (A copy of this deck is made for every new Round) */
    public static HeldJokers heldJokers;

    private static final Storage storage = Storage.getStorageInstance();

    /** Stores the play counts for each poker hand type */
    private static final Map<PokerHand.HandType, Integer> pokerHandPlayCounts =
            new EnumMap<>(PokerHand.HandType.class);

    // @author swethaiscool
    /**
     * Retrieves the current ante.
     *
     * @return the current {@link Ante} instance
     */
    public static Ante getAnte() {
        return ante;
    }

    /**
     * Retrieves the current round count.
     *
     * @return the current round count
     */
    public static int getRoundCount() {
        return roundCount;
    }

    /** Advances the game to the next round, updating the ante and incrementing the round count. */
    public void nextRound() {
        ante.nextRound();
        roundCount++;

        // Update ante and blind values and round count
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.ROUND_NUMBER_INDEX,
                String.valueOf(roundCount)); // Update Round Count
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.ANTE_NUMBER_INDEX,
                String.valueOf(ante.getAnteCount())); // Update Ante Count
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.BLIND_INDEX,
                String.valueOf(ante.getBlind().getName())); // Update Blind

        Round nextRound = classicRound();

        assert nextRound != null;
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.HAND_INDEX,
                String.valueOf(nextRound.getRemainingPlays())); // Update Number Of Plays
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.DISCARD_INDEX,
                String.valueOf(nextRound.getRemainingDiscards())); // Update Number Of Discards
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.ROUND_SCORE_INDEX,
                String.valueOf(nextRound.getCurrentScore())); // Update Current Score

        // Update Holding Hand
        for (int i = Storage.HOLDING_HAND_START_INDEX;
                i < Storage.HOLDING_HAND_START_INDEX + 8;
                i++) {
            Card currentCard =
                    nextRound.getPlayerHandCards().get(i - Storage.HOLDING_HAND_START_INDEX);
            storage.setValue(
                    storage.getRunChosen() - 1,
                    i,
                    Storage.cardToString(currentCard)); // Update Holding Hand Cards
        }

        // Update save file
        try {
            storage.updateSaveFile();
        } catch (JavatroException e) {
            System.out.println("Could not save info.");
        }

        startNewRound(nextRound);
    }

    /** Initializes a new game by resetting the ante, round count, jokers and decks. */
    public void setupNewGame(DeckType deckType) {
        ante = new Ante();

        ante.setBlind(
                Storage.BlindFromKey(
                        storage.getValue(storage.getRunChosen() - 1, Storage.BLIND_INDEX)));
        ante.setAnteCount(
                Integer.parseInt(
                        storage.getValue(storage.getRunChosen() - 1, Storage.ANTE_NUMBER_INDEX)));
        roundCount =
                Integer.parseInt(
                        storage.getValue(storage.getRunChosen() - 1, Storage.ROUND_NUMBER_INDEX));

        totalPlays = 4;
        heldJokers = new HeldJokers();

        // Update Jokers
        for (int i = Storage.JOKER_HAND_START_INDEX; i < Storage.JOKER_HAND_START_INDEX + 5; i++) {
            if (Objects.equals(storage.getValue(storage.getRunChosen() - 1, i), "-")) continue;

            try {
                heldJokers.add(
                        Storage.parseJokerString(storage.getValue(storage.getRunChosen() - 1, i)));
            } catch (JavatroException e) {
                throw new RuntimeException(e);
            }
        }

        deck = new Deck(deckType);
    }
    // @author swethaiscool

    /**
     * Starts a new round and assigns it to the current round.
     *
     * @param round The new round to start.
     */
    private static void startNewRound(Round round) {
        currentRound = round;
        // Set round number, discards and hands
        assert currentRound != null;

        // Update round attributes
        int savedPlays =
                Integer.parseInt(storage.getValue(storage.getRunChosen() - 1, Storage.HAND_INDEX));
        int savedDiscards =
                Integer.parseInt(
                        storage.getValue(storage.getRunChosen() - 1, Storage.DISCARD_INDEX));
        int savedScore =
                Integer.parseInt(
                        storage.getValue(storage.getRunChosen() - 1, Storage.ROUND_SCORE_INDEX));

        if (savedPlays != -1 && savedDiscards != -1) {
            round.updatePlays(savedPlays);
            round.updateDiscards(savedDiscards);
            round.setCurrentScore(savedScore);

            // Update deck with rest of the cards
            currentRound.getDeck().populateWithSavedDeck();
        }

        // Update savedCards
        List<Card> savedCards = new ArrayList<>();
        int emptyCardCount = 0;

        for (int i = Storage.HOLDING_HAND_START_INDEX;
                i < Storage.HOLDING_HAND_START_INDEX + 8;
                i++) {
            if (storage.getValue(storage.getRunChosen() - 1, i).equals("-")) {
                emptyCardCount = emptyCardCount + 1;
                continue;
            }
            savedCards.add(
                    Storage.parseCardString(storage.getValue(storage.getRunChosen() - 1, i)));
        }

        if (emptyCardCount < 8) {
            round.setPlayerHandCards(savedCards);
        }
    }

    /**
     * Creates a new classic round with predefined settings.
     *
     * @return A {@code Round} instance configured as a classic round.
     */
    private static Round classicRound() {
        Deck d;
        try {
            d = new Deck(deck);
            d.shuffle();
            return new Round(
                    ante, 4, d, heldJokers, ante.getBlind().getName(), ante.getBlind().getName());
        } catch (JavatroException javatroException) {
            System.out.println(javatroException.getMessage());
        }
        return null;
    }

    /**
     * Starts the game by initializing a new set of game parameters. This method is called when the
     * game begins.
     *
     * @throws JavatroException If an error occurs while starting the game.
     */
    public void beginGame() throws JavatroException {
        startNewRound(Objects.requireNonNull(classicRound()));
    }

    /** Initializes poker hand play counts at game start */
    public static void initializePokerHandStats() {
        for (PokerHand.HandType handType : PokerHand.HandType.values()) {
            pokerHandPlayCounts.put(handType, 0);
        }
    }

    /** Gets the play count for a specific hand type */
    public static int getPlayCount(PokerHand.HandType handType) {
        return pokerHandPlayCounts.getOrDefault(handType, 0);
    }

    /** Increments the play count for a specific hand type */
    public static void incrementPlayCount(PokerHand.HandType handType) {
        pokerHandPlayCounts.put(handType, getPlayCount(handType) + 1);
    }
}
