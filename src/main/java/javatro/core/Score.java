package javatro.core;

import java.util.ArrayList;
import java.util.List;

import javatro.core.jokers.HeldJokers;
import javatro.core.jokers.Joker;

/** Contains the algorithm for calculating the final score and played hand. */
public class Score {
    public double totalChips = 0;
    public double totalMultiplier = 0;

    /** Returns the score of the played hand by calculating the value of the hand. */
    public int getScore(PokerHand pokerHand, List<Card> playedCardList, HeldJokers heldJokers) throws JavatroException {

        //Highlight any boss blind effects that apply here
        //Cannot play suits, cannot play Face Cards, Must play 5 cards
        ArrayList<Joker> jokerList = heldJokers.getJokers();

        // First add pokerHand's chip and mult base to the scores.
        totalChips = (double) pokerHand.getChips();
        totalMultiplier = (double) pokerHand.getMultiplier();

        //Score the cards and apply any Jokers that have effects on play here.
        for (Card currentCard : playedCardList) {
            totalChips += currentCard.getChips();
            //From here, check each joker in heldJokers can be played.
            for (Joker currentJoker :jokerList) {
                currentJoker.interact(this, currentCard);
            }
        }

        //Apply any

        // Round the score and return the correct score value.
        Math.round(totalChips);
        Math.round(totalMultiplier);
        totalMultiplier = (int) Math.ceil(totalChips * totalMultiplier);
        return 0;
    }
}
