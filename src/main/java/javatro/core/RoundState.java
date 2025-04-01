package javatro.core;

import static javatro.core.Round.INITIAL_HAND_SIZE;

import javatro.core.jokers.HeldJokers;

import java.util.List;

/**
 * Encapsulates the state of a round in the game, including score, play limits, and player
 * resources.
 *
 * @see Round The main game round class that uses this state
 */
public class RoundState {
    /** The player's current score in the round. */
    private long currentScore;
    /** The number of remaining discards allowed. */
    private int remainingDiscards;
    /** The number of remaining plays in the round. */
    private int remainingPlays;
    /** The player's current held jokers. */
    private final HeldJokers playerJokers;
    /** The deck of cards used in the round. */
    private final Deck deck;
    /** The player's current hand. */
    private final HoldingHand playerHand;
    /** The cards previously played/discarded in the current round. */
    private List<Card> chosenCards;

    /**
     * Creates a new round state with initial values.
     *
     * @param currentScore The starting score for the round
     * @param remainingDiscards The initial number of discards available
     * @param remainingPlays The initial number of plays available
     * @param playerJokers The player's jokers for this round
     * @param deck The deck of cards to be used for this round
     */
    public RoundState(
            long currentScore,
            int remainingDiscards,
            int remainingPlays,
            HeldJokers playerJokers,
            Deck deck)
            throws JavatroException {
        this.currentScore = currentScore;
        this.remainingDiscards = remainingDiscards;
        this.remainingPlays = remainingPlays;
        this.playerJokers = playerJokers;
        this.deck = deck;
        this.playerHand = new HoldingHand();

        drawInitialCards(INITIAL_HAND_SIZE);
    }

    /**
     * Gets the current score accumulated in this round.
     *
     * @return The current score
     */
    public long getCurrentScore() {
        return currentScore;
    }

    /**
     * Adds points to the current score.
     *
     * @param points The number of points to add to the score
     */
    public void addScore(long points) {
        currentScore += points;
    }

    /**
     * Gets the number of remaining discards available to the player.
     *
     * @return The number of remaining discards
     */
    public int getRemainingDiscards() {
        return remainingDiscards;
    }

    /**
     * Sets the number of remaining discards to a specified amount.
     *
     * @param amount The number of discards to set
     * @throws IllegalArgumentException if the amount is negative
     */
    public void setRemainingDiscards(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        remainingDiscards = amount;
    }

    /** Reduces the number of remaining discards by one. */
    public void decrementDiscards() {
        remainingDiscards--;
    }

    /**
     * Increases the number of remaining discards by a specified amount.
     *
     * @param amount The number of additional discards to add
     */
    public void increaseRemainingDiscards(int amount) {
        remainingDiscards += amount;
    }

    /**
     * Gets the number of remaining plays available to the player.
     *
     * @return The number of remaining plays
     */
    public int getRemainingPlays() {
        return remainingPlays;
    }

    /** Reduces the number of remaining plays by one. */
    public void decrementPlays() {
        remainingPlays--;
    }

    /**
     * Set the number of remaining plays by a specified amount.
     *
     * @param amount The number of additional plays to set
     * @throws IllegalArgumentException if the amount is negative
     */
    public void setRemainingPlays(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        remainingPlays = amount;
    }

    /**
     * Gets the player's current held jokers.
     *
     * @return The player's jokers
     */
    public HeldJokers getPlayerJokers() {
        return playerJokers;
    }

    /**
     * Gets the deck used in this round.
     *
     * @return The deck object
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Gets the player's current hand of cards.
     *
     * @return The player's hand
     */
    public HoldingHand getPlayerHand() {
        return playerHand;
    }

    /**
     * Gets the player's current hand of cards as a list.
     *
     * @return The player's hand as a list of cards
     */
    public List<Card> getPlayerHandCards() {
        return playerHand.getHand();
    }

    /**
     * Sets the player hand cards.
     *
     * @param playerHandCards The new holding hand to set
     * @throws IllegalArgumentException if the player hand is null
     */
    public void setPlayerHandCards(List<Card> playerHandCards) throws IllegalArgumentException {
        if (playerHandCards == null) {
            throw new IllegalArgumentException("playerHandCards cannot be null");
        }
        this.playerHand.setHand(playerHandCards);
    }

    /**
     * Gets the cards that were last played or discarded.
     *
     * @return The list of chosen cards
     */
    public List<Card> getChosenCards() {
        return chosenCards;
    }

    /**
     * Sets the chosen cards.
     *
     * @param chosenCards The cards that were chosen
     */
    public void setChosenCards(List<Card> chosenCards) {
        this.chosenCards = chosenCards;
    }

    /**
     * Draws the initial cards for the player's hand.
     *
     * @param count The number of cards to draw
     * @throws JavatroException if the draw fails
     */
    public void drawInitialCards(int count) throws JavatroException {
        playerHand.draw(count, deck);
    }
}
