package Javatro;

public class Round {
    private int currentScore;
    private int blindScore;
    private int totalDiscards;
    private int totalPlays;
    private Deck deck;
    private HoldingHand playerHand;
    private UserInterface ui;

    /**
     * Constructs a new round with the specified blind score. The blind score can be fetched from a file or manually inputed.
     * 
     * @param blindScore
     *            The target score to beat in this round
     */
    public Round(int blindScore) {
        this.currentScore = 0;
        this.blindScore = blindScore;
        this.totalDiscards = 0;
        this.totalPlays = 0;
        this.deck = new Deck();
        this.deck.initialize();
        this.playerHand = new HoldingHand();
        this.ui = new UserInterface();   

        // Initial draw
        for (int i = 0; i < 8; i++) {
            playerHand.draw(deck.draw());
        }
    }

    /**
     * Plays a set of 5 cards as a poker hand.
     * 
     * @param cardIndices
     *            Indices of cards to play from the holding hand
     * @return The result of the poker hand
     */
    public HandResult playCards(int[] cardIndices) {
        if (cardIndices.length != 5) {
            throw new IllegalArgumentException("Must play exactly 5 cards");
        }

        Card[] playedCards = playerHand.playCards(cardIndices);
        PokerHand pokerHand = new PokerHand(playedCards);
        HandResult result  = pokerHand.evaluateHand();

        // Print results (or should it be done in PokerHand and HandResult?)
        ui.printPlayerHand(playerHand);
        ui.printHandResult(result);        

        // Update round state        
        currentScore += result.getScore() * result.getMultiplier();
        totalPlays++;

        ui.printState(currentScore, blindScore, totalDiscards, totalPlays);

        // Draw new cards to replace played ones
        for (int i = 0; i < cardIndices.length; i++) {
            if (deck.hasCards()) {
                playerHand.draw(deck.draw());
            }
        }

        return result;
    }

    /**
     * Discards cards from the player's hand.
     * 
     * @param cardIndices
     *            Indices of cards to discard
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

    public HoldingHand getPlayerHand() {
        return playerHand;
    }

    /**
     * Checks if the round is over based on game rules.
     * 
     * @return true if the round is over, false otherwise
     */
    public boolean isRoundOver() {
        // Round ends if we've reached maximum plays or score exceeds blind score
        return totalPlays >= 3 || currentScore > blindScore;
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