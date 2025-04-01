package javatro.core;

import static javatro.core.Round.DEFAULT_MAX_HAND_SIZE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Implements the game actions available in a round without direct Round dependencies. */
public class RoundActions {
    
    /**
     * Plays a set of cards as a poker hand.
     *
     * @param state The current state of the round
     * @param config The round configuration
     * @param cardIndices Indices of cards to play from the holding hand
     * @return ActionResult containing played cards, points earned, and state changes
     * @throws JavatroException If the play is invalid
     */
    public static ActionResult playCards(
            RoundState state,
            RoundConfig config,
            List<Integer> cardIndices) throws JavatroException {
        
        // Explicitly validate required components are available
        if (state == null || config == null || cardIndices == null) {
            throw new IllegalArgumentException("Required parameters cannot be null");
        }
        
        // Validate parameters
        validatePlayCards(cardIndices, 
                         config.getMinHandSize(), 
                         config.getMaxHandSize(), 
                         state.getRemainingPlays());

        // Execute play (without modifying state)
        List<Card> playedCards = state.getPlayerHand().play(cardIndices);
        PokerHand result = HandResult.evaluateHand(playedCards);
        Score handScore = new Score(config.getBossType());
        long pointsEarned = handScore.getScore(result, playedCards, state.getPlayerJokers());

        // Draw new cards to replace played ones
        state.getPlayerHand().draw(cardIndices.size(), state.getDeck());

        // Return results rather than modifying round directly
        return ActionResult.forPlay(playedCards, pointsEarned);
    }

    /**
     * Validates that a set of cards can be played.
     *
     * @param cardIndices Indices of cards to validate
     * @param minHandSize Minimum hand size allowed
     * @param maxHandSize Maximum hand size allowed
     * @param remainingPlays The number of plays remaining
     * @throws JavatroException If the play is invalid
     */
    private static void validatePlayCards(
            List<Integer> cardIndices, 
            int minHandSize,
            int maxHandSize,
            int remainingPlays) throws JavatroException {
            
        boolean isAcceptableHandSize =
                cardIndices.size() <= maxHandSize && cardIndices.size() >= minHandSize;

        if (!isAcceptableHandSize) {
            throw JavatroException.invalidPlayedHand(minHandSize, maxHandSize);
        }

        if (remainingPlays <= 0) {
            throw JavatroException.noPlaysRemaining();
        }

        assert !cardIndices.isEmpty() : "Card indices cannot be empty";
        assert cardIndices.size() <= maxHandSize : "Cannot play more than " + maxHandSize + " cards";
        assert remainingPlays > 0 : "No plays remaining to execute this action";
    }

    /**
     * Discards cards from the player's hand.
     *
     * @param state The current state of the round
     * @param config The configuration of the round
     * @param cardIndices Indices of cards to discard from the holding hand
     * @return ActionResult containing discarded cards and state changes
     * @throws JavatroException If the discard is invalid
     */
    public static ActionResult discardCards(
            RoundState state,
            RoundConfig config,
            List<Integer> cardIndices) throws JavatroException {
            
        assert cardIndices != null : "Card indices cannot be null";

        validateDiscardCards(cardIndices, state.getRemainingDiscards());

        // Handle duplicates by using a Set
        Set<Integer> indicesToDiscard = new HashSet<>(cardIndices);

        // Execute discard
        List<Card> discardedCards = state.getPlayerHand().discard(cardIndices);
        state.getPlayerHand().draw(indicesToDiscard.size(), state.getDeck());

        // Return results rather than modifying round directly
        return ActionResult.forDiscard(discardedCards);
    }

    /**
     * Validates that a set of cards can be discarded.
     *
     * @param cardIndices Indices of cards to validate
     * @param remainingDiscards The number of discards remaining
     * @throws JavatroException If the discard is invalid
     */
    private static void validateDiscardCards(
            List<Integer> cardIndices, 
            int remainingDiscards) throws JavatroException {
            
        int numberOfDiscards = cardIndices.size();

        if (remainingDiscards <= 0) {
            throw JavatroException.noRemainingDiscards();
        }

        if (numberOfDiscards > DEFAULT_MAX_HAND_SIZE) {
            throw JavatroException.tooManyDiscards();
        }

        if (cardIndices.size() < 1) {
            throw JavatroException.cannotDiscardZeroCards();
        }

        assert !cardIndices.isEmpty() : "Cannot discard zero cards";
        assert remainingDiscards > 0 : "No discards remaining to execute this action";
    }
    
    /**
     * Holds the result of a card action (play or discard).
     */
    public static class ActionResult {
        private final List<Card> cards;
        private final long pointsEarned;
        
        public ActionResult(List<Card> cards, long pointsEarned) {
            this.cards = cards;
            this.pointsEarned = pointsEarned;
        }
        
        public List<Card> getCards() {
            return cards;
        }
        
        public long getPointsEarned() {
            return pointsEarned;
        }

        /**
         * Creates a result for a play action.
         *
         * @param cards The cards that were played
         * @param pointsEarned The points earned from the play
         * @return An ActionResult representing a play action
         */
        public static ActionResult forPlay(List<Card> cards, long pointsEarned) {
            return new ActionResult(cards, pointsEarned);
        }
        
        /**
         * Creates a result for a discard action.
         *
         * @param cards The cards that were discarded
         * @return An ActionResult representing a discard action
         */
        public static ActionResult forDiscard(List<Card> cards) {
            return new ActionResult(cards, 0);
        }
    }
}
