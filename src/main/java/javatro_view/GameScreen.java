package javatro_view;

import java.util.Arrays;
import java.util.List;

public class GameScreen extends Screen{

  private static final List<String> options = Arrays.asList("Discard Hand", "See Target Score", "Play Hand", "Exit Game");
  private static final String blindType = "SMALL";
  private static final int roundScore = 0;
  private static final int handsLeft = 0;
  private static final int moneyInfo = 0;
  private static final int discardCount = 0;
  private static final int currentRound = 0;

  public GameScreen() {
    super(options);
  }

  //Shows all necessary components in a compact view
  public void displayGameScreenUI() {
    System.out.println("+------------------------------------------------+");
    System.out.println("|          " + blindType + " BLIND                           |"); //See blind type
    System.out.println("|  [● " + blindType + " BLIND]  Score: 300   Reward: $$$     |");
    System.out.println("|------------------------------------------------|");
    System.out.println("|  Round Score: ○" +  roundScore + "                               |");
    System.out.println("|------------------------------------------------|");
    System.out.println("|  Run Info                                      |");
    System.out.println("|  Hands: " + handsLeft + "      Discards: " + discardCount + "                    |");
    System.out.println("|  Money: $" + moneyInfo + "                                    |");
    System.out.println("|------------------------------------------------|");
    System.out.println("|  Options                                       |");
    System.out.println("|  Round: "+ currentRound + "                               |");
    System.out.println("|------------------------------------------------|");
    System.out.println("|  Deck: [▒▒▒▒▒▒]    Cards Left: 44/52           |");
    System.out.println("|------------------------------------------------|");
    System.out.println("|                                                |");
    System.out.println("|  [ K♠ ] [ Q♠ ] [ 10♥ ] [ 9♣ ] [ 8♣ ] [ 6♠ ]   |");
    System.out.println("|  [ 5♦ ] [ 3♦ ]                                |");
    System.out.println("+------------------------------------------------+");


  }
  // Method that shows Current Round
  public void displayRound() {}

  // Method that displays blind score
  public void displayBlindScore() {}

  // Method that displays current score
  public void displayCurrentScore() {}

  // Method that displays the holding hand
  public void displayHoldingHand() {}

  // Method that displays the number of plays
  public void displayPlays() {}

  // Method that displays the number of discards so far
  public void displayDiscards() {}

  // Method that displays the hand selected to play
  public void displayPlayingHand() {}


  @Override
  public void displayScreen() {
    displayGameScreenUI();
    super.displayOptions();
  }
}
