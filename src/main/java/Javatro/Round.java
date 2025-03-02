package Javatro;

import java.util.List;

public class Round {
    private static final int DEFAULT_BLIND_SCORE = 100;
    private static final int INITIAL_HAND_SIZE = 8;
    private static final int POKER_HAND_SIZE = 5;
    private static final int MAX_PLAYS_PER_ROUND = 3;
    private static final int MAX_DISCARDS_PER_ROUND = 3;

    private int currentScore;
    private int blindScore;
    private int remainingDiscards;
    private int totalPlays;
    private Deck deck;
    private PokerHand pokerHand;
    private HoldingHand playerHand;
    private State gameState;

    /**
     * Constructs a new round with the specified blind score. The blind score can be fetched from a
     * file or manually inputed.
     *
     * @param blindScore The target score to beat in this round
     */
    public Round(State gameState) {
        this.gameState = gameState;
        this.currentScore = 0;
        this.blindScore = gameState.getBlindScore();
        this.remainingDiscards = MAX_DISCARDS_PER_ROUND;
        this.totalPlays = 0;
        this.deck = gameState.getDeck();
        this.pokerHand = new PokerHand();
        this.playerHand = new HoldingHand();

        // Initial draw
        playerHand.draw(INITIAL_HAND_SIZE);
    }

    /**
     * Plays a set of 5 cards as a poker hand.
     *
     * @param cardIndices Indices of cards to play from the holding hand
     * @return The result of the poker hand
     */
    public void playCards(int[] cardIndices) {
        if (cardIndices.length != POKER_HAND_SIZE) {
            throw new IllegalArgumentException("Must play exactly " + POKER_HAND_SIZE + " cards");
        }

        List<Card> playedCards = playerHand.playCards(cardIndices);
        HandResult result = PokerHand.evaluateHand(playedCards);
        Integer totalChips = result.chips();

        for (Card card : playedCards) {
            totalChips += card.getChips();
        }

        // Update round state
        currentScore += totalChips * result.multiplier();

        // Draw new cards to replace played ones
        playerHand.draw(POKER_HAND_SIZE);

        totalPlays++;
    }

    /**
     * Discards cards from the player's hand.
     *
     * @param cardIndices Indices of cards to discard
     */
    public void discardCards(int[] cardIndices) {
        if (remainingDiscards <= 0) {
            throw new IllegalStateException("No remaining discards available");
        }
        playerHand.discardCards(cardIndices);
        remainingDiscards--;

        playerHand.draw(cardIndices.length);
    }

    // Getters
    public int getCurrentScore() {
        return currentScore;
    }

    public int getBlindScore() {
        return blindScore;
    }

    public int getRemainingDiscards() {
        return remainingDiscards;
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
        return totalPlays >= MAX_PLAYS_PER_ROUND || this.isWon();
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
