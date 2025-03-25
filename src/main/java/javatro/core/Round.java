package javatro.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Round {
    /** The initial number of cards dealt to the player. */
    public static final int INITIAL_HAND_SIZE = 8;
    /** The maximum number of discards allowed per round. */
    public static final int MAX_DISCARDS_PER_ROUND = 4;
    /** The number of cards required to form a valid poker hand. */
    private static final int POKER_HAND_SIZE = 5;

    /** The player's current score in the round. */
    private int currentScore;
    /** The minimum score required to win the round. */
    private int blindScore;
    /** The number of remaining discards allowed. */
    private int remainingDiscards;
    /** The number of remaining plays in the round. */
    private int remainingPlays;

    /** The deck of cards used in the round. */
    private Deck deck;
    /** The player's current hand of cards. */
    private HoldingHand playerHand;

    /** The name of the current round. */
    private String roundName = "";
    /** The description of the current round. */
    private String roundDescription = "";

    /** Manages property change listeners for game state updates. */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this); // Observable

    /**
     * Constructs a new round with the specified blind score. The blind score can be fetched from a
     * file or manually inputed.
     *
     * <p>//@param gameState The current state of the game.
     *
     * @throws JavatroException
     */
    public Round(
            int blindScore,
            int remainingPlays,
            Deck deck,
            String roundName,
            String roundDescription)
            throws JavatroException {
        this.currentScore = 0;
        this.blindScore = blindScore;
        this.remainingDiscards = MAX_DISCARDS_PER_ROUND;
        this.remainingPlays = remainingPlays;
        this.deck = deck;
        this.playerHand = new HoldingHand();

        // Default descriptions and names
        this.roundName = roundName;
        this.roundDescription = roundDescription;

        if (blindScore < 0) {
            throw JavatroException.invalidBlindScore();
        }

        if (remainingPlays <= 0) {
            throw JavatroException.invalidPlaysPerRound();
        }

        if (deck == null) {
            throw JavatroException.invalidDeck();
        }

        // Initial draw
        playerHand.draw(INITIAL_HAND_SIZE, this.deck);

        // Post-construction invariants
        assert this.currentScore == 0 : "Initial score must be zero";
        assert this.remainingDiscards == MAX_DISCARDS_PER_ROUND
                : "Initial discards must be set to maximum";
        assert this.playerHand.getHand().size() == INITIAL_HAND_SIZE
                : "Player should have exactly " + INITIAL_HAND_SIZE + " cards initially";
    }

    /**
     * Registers an observer to listen for property changes.
     *
     * @param pcl The property change listener to register.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Fires property change events to notify observers of updated round variables. This method
     * updates display components and other observers about the current game state.
     */
    public void updateRoundVariables() {
        support.firePropertyChange("blindScore", null, blindScore);
        support.firePropertyChange("remainingPlays", null, remainingPlays);
        support.firePropertyChange("remainingDiscards", null, remainingDiscards);
        support.firePropertyChange("roundName", null, roundName);
        support.firePropertyChange("roundDescription", null, roundDescription);
        support.firePropertyChange("holdingHand", null, getPlayerHand());
        support.firePropertyChange("currentScore", null, currentScore);

        if (isRoundOver() && isWon()) {
            support.firePropertyChange("roundComplete", null, 1);
        } else if (isRoundOver() && !isWon()) {
            support.firePropertyChange("roundComplete", null, -1);
        } else {
            support.firePropertyChange("roundComplete", null, 0);
        }
    }

    /**
     * Plays a set of 5 cards as a poker hand.
     *
     * @param cardIndices Indices of cards to play from the holding hand (must be exactly 5 cards)
     * @throws JavatroException If the number of cards played is not equal to 5
     */
    public void playCards(List<Integer> cardIndices) throws JavatroException {
        if (cardIndices.size() > POKER_HAND_SIZE || cardIndices.isEmpty()) {
            throw JavatroException.invalidPlayedHand();
        }

        if (remainingPlays <= 0) {
            throw JavatroException.noPlaysRemaining();
        }

        assert cardIndices != null : "Card indices cannot be null";
        assert !cardIndices.isEmpty() : "Card indices cannot be empty";
        assert cardIndices.size() <= POKER_HAND_SIZE
                : "Cannot play more than " + POKER_HAND_SIZE + " cards";
        assert remainingPlays > 0 : "No plays remaining to execute this action";

        int oldScore = currentScore;
        int oldRemainingPlays = remainingPlays;

        List<Card> playedCards = playerHand.play(cardIndices, this.deck);
        PokerHand result = HandResult.evaluateHand(playedCards);
        Integer totalChips = result.getChips();

        for (Card card : playedCards) {
            totalChips += card.getChips();
        }

        // Update round state
        currentScore += totalChips * result.getMultiplier();

        // Draw new cards to replace played ones
        playerHand.draw(cardIndices.size(), deck);

        remainingPlays--;

        // Post-condition assertions
        assert remainingPlays == oldRemainingPlays - 1
                : "Remaining plays should decrease by exactly 1";
        assert currentScore >= oldScore : "Score should not decrease after playing cards";
        assert playerHand.getHand().size() == INITIAL_HAND_SIZE
                : "Hand size should be maintained after play";

        updateRoundVariables();
    }

    /**
     * Discards cards from the player's hand.
     *
     * @param cardIndices Indices of cards to discard from the holding hand
     * @throws IllegalStateException If no remaining discards are available
     */
    public void discardCards(List<Integer> cardIndices) throws JavatroException {
        if (remainingDiscards <= 0) {
            throw new JavatroException("No remaining discards available");
        }

        if (cardIndices.size() > remainingDiscards) {
            throw new JavatroException("Too many discards");
        }

        if (cardIndices.size() < 1) {
            throw new JavatroException("Cannot discard zero cards");
        }

        assert cardIndices != null : "Card indices cannot be null";
        assert !cardIndices.isEmpty() : "Cannot discard zero cards";
        assert remainingDiscards > 0 : "No discards remaining to execute this action";

        // Handle duplicates by using a Set
        Set<Integer> indicesToDiscard = new HashSet<>(cardIndices);

        int handSizeBefore = playerHand.getHand().size();
        int oldRemainingDiscards = remainingDiscards;

        playerHand.discard(cardIndices, deck);
        remainingDiscards--;

        playerHand.draw(indicesToDiscard.size(), deck);

        // Post-condition assertions
        assert remainingDiscards == oldRemainingDiscards - 1
                : "Remaining discards should decrease by exactly 1";
        assert playerHand.getHand().size() == handSizeBefore
                : "Hand size should be maintained after discard";

        updateRoundVariables();
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

    public int getRemainingPlays() {
        return remainingPlays;
    }

    public List<Card> getPlayerHand() {
        assert playerHand != null : "Player hand cannot be null";
        return playerHand.getHand();
    }

    /**
     * Checks if the round is over based on game rules.
     *
     * @return true if the round is over, false otherwise
     */
    public boolean isRoundOver() {
        // Round ends if no plays are remaining
        return remainingPlays <= 0;
    }

    /**
     * Determines if the round was won.
     *
     * @return true if player won the round, false otherwise
     */
    public boolean isWon() {
        assert isRoundOver() || remainingPlays > 0 : "Round state is inconsistent";
        return currentScore >= blindScore & isRoundOver();
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getRoundDescription() {
        return roundDescription;
    }

    public void setRoundDescription(String roundDescription) {
        this.roundDescription = roundDescription;
    }
}
