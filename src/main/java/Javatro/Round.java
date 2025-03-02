package Javatro;

import java.util.List;

public class Round {
    private static final int DEFAULT_BLIND_SCORE = 100;
    private static final int INITIAL_HAND_SIZE = 8;
    private static final int POKER_HAND_SIZE = 5;
    private static final int MAX_PLAYS_PER_ROUND = 3;
    
    private int currentScore;
    private int blindScore;
    private int totalDiscards;
    private int totalPlays;
    private Deck deck;
    private PokerHand pokerHand;
    private HoldingHand playerHand;

    /**
     * Constructs a new round with the specified blind score. The blind score can be fetched from a
     * file or manually inputed.
     *
     * @param blindScore The target score to beat in this round
     */
    public Round(int blindScore) {
        this.currentScore = 0;
        this.blindScore = blindScore;
        this.totalDiscards = 0;
        this.totalPlays = 0;
        this.deck = new Deck();
        this.deck.initialize();
        this.pokerHand = new PokerHand();
        this.playerHand = new HoldingHand();

        // Initial draw
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            playerHand.draw(deck.draw());
        }
    }

    /** Constructs a new round with the default blind score of 100. */
    public Round() {
        this(DEFAULT_BLIND_SCORE);
    }

    /**
     * Plays a set of 5 cards as a poker hand.
     *
     * @param cardIndices Indices of cards to play from the holding hand
     * @return The result of the poker hand
     */
    public int playCards(int[] cardIndices) {
        if (cardIndices.length != POKER_HAND_SIZE) {
            throw new IllegalArgumentException("Must play exactly " + POKER_HAND_SIZE + " cards");
        }

        List<Card> playedCards = playerHand.playCards(cardIndices);
        HandResult result = PokerHand.evaluateHand(playedCards);

        // Update round state
        currentScore += result.chips() * result.multiplier();

        // Draw new cards to replace played ones
        for (int i = 0; i < cardIndices.length; i++) {
            if (deck.hasCards()) {
                playerHand.draw(deck.draw());
            }
        }

        totalPlays++;
        return score;
    }

    /**
     * Discards cards from the player's hand.
     *
     * @param cardIndices Indices of cards to discard
     */
    public void discardCards(int[] cardIndices) {
        playerHand.discardCards(cardIndices);
        totalDiscards++;

        // Draw new cards to replace discarded ones
        for (int i = 0; i < cardIndices.length; i++) {
            if (deck.hasCards()) {
                playerHand.draw(deck.draw());
            }
        }
    }

    // Getters
    public int getCurrentScore() {
        return currentScore;
    }

    public int getBlindScore() {
        return blindScore;
    }

    public int getTotalDiscards() {
        return totalDiscards;
    }

    public int getTotalPlays() {
        return totalPlays;
    }

    /**
     * Checks if the round is over based on game rules.
     *
     * @return true if the round is over, false otherwise
     */
    public boolean isRoundOver() {
        // Round ends if we've reached maximum plays or score exceeds blind score
        return totalPlays >= MAX_PLAYS_PER_ROUND || currentScore > blindScore;
    }

    /**
     * Determines if the round was won.
     *
     * @return true if player won the round, false otherwise
     */
    public boolean isWon() {
        return currentScore > blindScore;
    }
}
