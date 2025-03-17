package javatro_view;

import static javatro_view.GameScreen.getDisplayStringCenter;
import static javatro_view.GameScreen.getHeaderString;

import java.util.List;
import javatro_core.Card;
import javatro_core.JavatroCore;
import javatro_manager.JavatroManager;
import javatro_manager.LoadGameScreenCommand;
import javatro_manager.MakeSelectionCommand;
import javatro_manager.ResumeGameCommand;

public class SelectionScreen extends Screen{

  protected int SELECTION_LIMIT;
  List<Card> holdingHand;

  public SelectionScreen() {
    super("SELECT CARDS");
    commandMap.add(new ResumeGameCommand());
  }

  public void updateHoldingHand() {
    holdingHand = JavatroCore.currentRound.getPlayerHand();
  }


  protected void displayCardsInHoldingHand() {
    updateHoldingHand();
    // Get the card header for all cards in holding hand
    System.out.println("YOUR CARDS:");
    String numberHeaders = "";
    for(int i = 0;i<holdingHand.size();i++) {
      numberHeaders +=
          "|" + GameScreen.getDisplayStringCenter(Integer.toString(i+1),5)
              + "|"
              + "    ";
    }

    String cardHeaders = (GameScreen.getHeaderString(5) + "    ").repeat(holdingHand.size());
    System.out.println(numberHeaders);
    System.out.println(cardHeaders);

    String cardValues = "";
    for(int i = 0;i<holdingHand.size();i++) {
      cardValues +=
          "|" + GameScreen.getDisplayStringCenter(holdingHand.get(i).getSimplified(),5)
          + "|"
          + "    ";
    }

    // Printing the card value itself
    System.out.println(cardValues);

    // Printing the bottom header
    System.out.println(cardHeaders);
  }


  @Override
  public void displayScreen() {

  }
}
