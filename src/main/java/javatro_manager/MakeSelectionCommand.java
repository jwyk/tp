package javatro_manager;

import Javatro.JavatroException;
import java.util.List;
import javatro_core.JavatroCore;
import javatro_view.JavatroView;

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
    List<Integer> userInput = JavatroView.getCardInput(JavatroCore.currentRound.getPlayerHand().size(),LIMIT);

    //Based on this input, select those cards in the holdingHand
    JavatroCore.currentRound.playCards(userInput);

    //Return back to the game screen
    JavatroManager.setScreen(JavatroView.getGameScreen());

  }
}
