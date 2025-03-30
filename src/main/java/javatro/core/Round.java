package javatro.core;

import javatro.core.Deck.DeckType;
import javatro.core.jokers.HeldJokers;

import java.beans.PropertyChangeListener;
import java.util.List;

/** Represents a round in the Javatro game. */
public class Round {
    /** The type of boss in the round. */
    public enum BossType {
        NONE,
        THE_NEEDLE,
        THE_WATER,
        THE_CLUB,
        THE_WINDOW,
        THE_HEAD,
        THE_GOAD,
        THE_PLANT,
        THE_PSYCHIC
    }

    /** The initial number of cards dealt to the player. */
    public static final int INITIAL_HAND_SIZE = 8;
    /** The maximum number of discards allowed per round. */
    public static final int MAX_DISCARDS_PER_ROUND = 4;
    /** The maximum number of cards in a hand. */
    public static final int DEFAULT_MAX_HAND_SIZE = 5;
    /** The minimum number of cards in a hand. */
    public static final int DEFAULT_MIN_HAND_SIZE = 1;

    /** The player's current hand. */
    private HoldingHand playerHand;
    /** The player's current held jokers. */
    private HeldJokers playerJokers;

    /** The cards played in the current round. */
    private List<Card> playedCards;

    /** The state of the current round. */
    private final RoundState state;
    /** The configuration of the current round. */
    private final RoundConfig config;
    /** The actions available in the current round. */
    private final RoundActions actions;
    /** The observer pattern implementation. */
    private final RoundObservable observable;

    /** The deck of cards used in the round. */
    private Deck deck;

    private BossType bossType = BossType.NONE;

    /**
     * Constructs a new round with detailed configuration.
     *
     * @param ante The ante configuration containing the target score for this round
     * @param remainingPlays The initial number of plays allowed in this round
     * @param deck The deck of cards to be used for this round
     * @param heldJokers The player's collection of jokers available for this round
     * @param roundName The display name for this round
     * @param roundDescription A detailed description of the round's rules or theme
     * @throws JavatroException If any of the provided parameters are invalid
     */
    public Round(
            Ante ante,
            int remainingPlays,
            Deck deck,
            HeldJokers heldJokers,
            String roundName,
            String roundDescription)
            throws JavatroException {

        validateParameters(ante, remainingPlays, deck);

        this.deck = deck;
        this.playerHand = new HoldingHand();
        this.playerJokers = heldJokers;

        // Create config with round name and description
        this.config = new RoundConfig(roundName, roundDescription, ante.getRoundScore());

        // Create state with initial values
        this.state = new RoundState(0, MAX_DISCARDS_PER_ROUND, remainingPlays);

        // Apply special deck modifications
        applyDeckVariants(deck);

        // Apply special rules based on ante configuration
        applyAnteInvariants(ante);

        // Apply boss type
        applyBossVariants();

        // Create observable for notifications
        this.observable = new RoundObservable(this);

        // Create actions for game mechanics
        this.actions = new RoundActions(this);

        // Initial draw
        playerHand.draw(INITIAL_HAND_SIZE, deck);

        validatePostConstruction();
    }

    /**
     * Validates the input parameters for round creation.
     *
     * @param ante The ante configuration to validate
     * @param remainingPlays The number of plays to validate
     * @param deck The deck to validate
     * @throws JavatroException If validation fails for any parameter
     */
    private void validateParameters(Ante ante, int remainingPlays, Deck deck)
            throws JavatroException {
        if (ante.getRoundScore() < 0) {
            throw JavatroException.invalidBlindScore();
        }
        if (remainingPlays <= 0) {
            throw JavatroException.invalidPlaysPerRound();
        }
        if (deck == null) {
            throw JavatroException.invalidDeck();
        }
    }

    /**
     * Applies special rules based on the deck variant.
     *
     * @param deck The deck to check for special variants
     */
    private void applyDeckVariants(Deck deck) {
        DeckType deckName = deck.getDeckName();
        if (deckName == DeckType.RED) {
            this.state.increaseRemainingDiscards(1);
        } else if (deckName == DeckType.BLUE) {
            this.state.setRemainingPlays(this.state.getRemainingPlays() + 1);
        }
    }

    /**
     * Applies special rules based on the ante configuration.
     *
     * @param ante The ante configuration to check for special rules
     */
    private void applyAnteInvariants(Ante ante) {
        if (ante.getBlind() == Ante.Blind.BOSS_BLIND) {
            this.bossType = getRandomBoss();
        }
    }

    /**
     * Gets a random boss type.
     *
     * @return A randomly selected boss type
     */
    private BossType getRandomBoss() {

        // skip the first value (NONE) to ensure a valid boss type is selected
        int randomIndex = (int) (Math.random() * BossType.values().length - 1);
        assert randomIndex >= 0 && randomIndex < BossType.values().length - 1
                : "Random index out of bounds";
        return BossType.values()[randomIndex + 1];
    }

    /** Applies special rules based on the selected boss type. */
    private void applyBossVariants() {
        this.config.setMaxHandSize(DEFAULT_MAX_HAND_SIZE);
        this.config.setMinHandSize(1);
        switch (this.bossType) {
            case THE_NEEDLE:
                this.config.setMaxHandSize(1);
                break;
            case THE_WATER:
                this.state.setRemainingDiscards(0);
                break;
            case THE_PSYCHIC:
                this.config.setMaxHandSize(5);
                this.config.setMinHandSize(5);
                break;
            case NONE:
            default:
                break;
        }
    }

    /** Validates the post-construction state of the round. */
    private void validatePostConstruction() {
        assert this.state.getCurrentScore() == 0 : "Initial score must be zero";
        assert this.playerHand.getHand().size() == INITIAL_HAND_SIZE
                : "Player should have exactly " + INITIAL_HAND_SIZE + " cards initially";
    }

    /**
     * Constructs a new round with the specified ante and blind settings without specifying round
     * name and description.
     */
    public Round(Ante ante, int remainingPlays, Deck deck, HeldJokers heldJokers)
            throws JavatroException {
        this(ante, remainingPlays, deck, heldJokers, "Default Round", "Default Description");
    }

    /**
     * Registers a listener for round state changes.
     *
     * @param pcl The property change listener to register
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        observable.addPropertyChangeListener(pcl);
    }

    /** Notifies all registered observers of changes in the round state. */
    public void updateRoundVariables() {
        observable.updateRoundVariables();
    }

    /**
     * Plays a selection of cards from the player's hand.
     *
     * @param cardIndices Indices of cards to play from the holding hand
     * @throws JavatroException If the play is invalid or no plays remain
     * @see RoundActions#playCards(List)
     */
    public void playCards(List<Integer> cardIndices) throws JavatroException {
        playedCards = actions.playCards(cardIndices);
    }

    /**
     * Discards a selection of cards from the player's hand.
     *
     * @param cardIndices Indices of cards to discard from the holding hand
     * @throws JavatroException If the discard is invalid or no discards remain
     * @see RoundActions#discardCards(List)
     */
    public void discardCards(List<Integer> cardIndices) throws JavatroException {
        actions.discardCards(cardIndices);
    }

    /**
     * Gets the current score accumulated in this round.
     *
     * @return The current score
     */
    public long getCurrentScore() {
        return state.getCurrentScore();
    }

    /**
     * Gets the target score needed to win this round.
     *
     * @return The blind score target
     */
    public int getBlindScore() {
        return config.getBlindScore();
    }

    /**
     * Gets the number of remaining discards available to the player.
     *
     * @return The number of remaining discards
     */
    public int getRemainingDiscards() {
        return state.getRemainingDiscards();
    }

    /**
     * Gets the number of remaining plays available to the player.
     *
     * @return The number of remaining plays
     */
    public int getRemainingPlays() {
        return state.getRemainingPlays();
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
     * Gets the player's current held jokers.
     *
     * @return The player's jokers
     */
    public HeldJokers getPlayerJokers() {
        return playerJokers;
    }

    /**
     * Checks if the game is lost based on game rules.
     *
     * @return true if the game is lost, false otherwise
     */
    public boolean isLost() {
        // Game ends if no plays are remaining
        return state.getRemainingPlays() <= 0 && !isWon();
    }

    /**
     * Checks if the round was won by the player.
     *
     * @return true if the player won the round, false otherwise
     */
    public boolean isWon() {
        return state.getCurrentScore() >= config.getBlindScore();
    }

    /**
     * Checks if the round is over based on game rules.
     *
     * @return true if the game is lost, false otherwise
     */
    public boolean isRoundOver() {
        // Round ends if no plays are remaining
        return state.getRemainingPlays() <= 0 | isWon();
    }

    /**
     * Gets the display name of this round.
     *
     * @return The round name
     */
    public String getRoundName() {
        return config.getRoundName();
    }

    /**
     * Sets the display name of this round.
     *
     * @param roundName The new round name
     */
    public void setRoundName(String roundName) {
        config.setRoundName(roundName);
    }

    /**
     * Gets the description of this round.
     *
     * @return The round description
     */
    public String getRoundDescription() {
        return config.getRoundDescription();
    }

    /**
     * Sets the description of this round.
     *
     * @param roundDescription The new round description
     */
    public void setRoundDescription(String roundDescription) {
        config.setRoundDescription(roundDescription);
    }

    /**
     * Gets the state object for this round.
     *
     * @return The round state object
     */
    public RoundState getState() {
        return state;
    }

    /**
     * Gets the configuration object for this round.
     *
     * @return The round configuration object
     */
    public RoundConfig getConfig() {
        return config;
    }

    /**
     * Gets the observable object for this round.
     *
     * @return The round observable object
     */
    public RoundObservable getObservable() {
        return observable;
    }

    /**
     * Gets the boss type for this round.
     *
     * @return The boss type
     */
    public BossType getBossType() {
        return bossType;
    }

    /**
     * Sets the boss type for this round.
     *
     * @warning This method is not intended for use in normal gameplay. It is only for testing
     *     purposes.
     * @param bossType The new boss type
     */
    public void setBossType(BossType bossType) {
        this.bossType = bossType;
        applyBossVariants();
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
     * Gets the actions object for this round.
     *
     * @return The round actions object
     */
    public RoundActions getActions() {
        return actions;
    }

    /**
     * Gets the played cards in this round.
     *
     * @return The list of played cards
     */
    public List<Card> getPlayedCards() {
        return playedCards;
    }

    /**
     * Gets the played hand of cards in this round.
     *
     * @return The poker hand evaluated from the played cards
     * @throws JavatroException If the played cards are invalid or empty
     */
    public PokerHand getPlayedHand() throws JavatroException {
        return HandResult.evaluateHand(playedCards);
    }

    /**
     * @warn This method will be deprecated in future versions. Reordering of player hands should be
     *     done in UI and not in Round class. Updates the player hand.
     * @param playerHandCards The new holding hand to set
     * @throws IllegalArgumentException if the player hand is null
     */
    public void setPlayerHandCards(List<Card> playerHandCards) {
        if (playerHandCards == null) {
            throw new IllegalArgumentException("playerHandCards cannot be null");
        }
        // Make sure to update the actual HoldingHand object to match
        this.playerHand.setHand(playerHandCards);
    }
}
