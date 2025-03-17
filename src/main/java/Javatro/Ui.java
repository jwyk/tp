package Javatro;

import javatro_core.HoldingHand;
import javatro_core.PokerHand;

public class Ui {

    /**
     * Prints the result of a played hand.
     *
     * @param result The result of the hand
     * @param totalChips The total chips won from the hand
     * @param currentScore The current score after playing the hand
     */
    public void printHandResult(PokerHand result, int totalChips, int currentScore) {
        String line =
                "-----------------------------------------------------------"; // Horizontal line
                                                                               // for separation
        String handResult = String.format("Hand Result: %-20s", result);
        String chipsWon = String.format("Total Chips Won: %-10d", totalChips);
        String score = String.format("Current Score: %-10d", currentScore);

        System.out.println("\n" + line);
        System.out.println("\033[1;34m" + "=== HAND RESULT ===" + "\033[0m");
        System.out.println(line);
        System.out.println(handResult);
        System.out.println(chipsWon);
        System.out.println(score);
        System.out.println(line);
        System.out.println();
    }

    /**
     * Prints the result of discarding cards.
     *
     * @param remainingDiscards The remaining number of discards
     */
    public void printDiscardResult(int remainingDiscards) {
        String line = "--------------------------------------------";
        String discardHeader = "\033[1;33m" + "=== DISCARD RESULT ===" + "\033[0m";
        String remainingDiscardsMessage =
                String.format("Remaining Discards: %-10d", remainingDiscards);

        System.out.println("\n" + line);
        System.out.println(discardHeader);
        System.out.println(line);
        System.out.println(remainingDiscardsMessage);
        System.out.println(line);
        System.out.println();
    }

    /**
     * Prints the player's hand.
     *
     * @param playerHand The player's hand
     */
    public void printPlayerHand(HoldingHand playerHand) {
        // <Skeleton code> to print the actual player's hand in the console
        System.out.println("Your Hand: XXX");
    }
}
