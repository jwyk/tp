package javatro_manager;

import Javatro.JavatroException;
import java.util.List;
import javatro_core.JavatroCore;
import javatro_view.JavatroView;
import javatro_view.SelectCardsToDiscardScreen;
import javatro_view.SelectCardsToPlayScreen;

public class MakeSelectionCommand implements Command{

  private final int LIMIT;

  public MakeSelectionCommand(int LIMIT) {
    this.LIMIT = LIMIT;
  }

  @Override
  public String getDescription() {
    return "Make Selection";
  }



  @Override
  public void execute() throws JavatroException {

    List<Integer> userInput;

    if(LIMIT == -1) {
      userInput = JavatroView.getCardInput(JavatroCore.currentRound.getPlayerHand().size(),JavatroCore.currentRound.getPlayerHand().size());
    }else {
      userInput = JavatroView.getCardInput(JavatroCore.currentRound.getPlayerHand().size(),LIMIT);
    }

    if(JavatroView.getCurrentScreen() instanceof SelectCardsToPlayScreen) {
      //Based on this input, select those cards in the holdingHand
      JavatroCore.currentRound.playCards(userInput);
    }else if(JavatroView.getCurrentScreen() instanceof SelectCardsToDiscardScreen) {
      JavatroCore.currentRound.discardCards(userInput);
    }

    //Return back to the game screen
    JavatroManager.setScreen(JavatroView.getGameScreen());

  }
}
