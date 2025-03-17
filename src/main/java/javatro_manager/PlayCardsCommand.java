package javatro_manager;

import Javatro.JavatroException;
import javatro_view.JavatroView;

public class PlayCardsCommand implements Command{

  @Override
  public void execute() throws JavatroException {

    // Update the main screen to show select cards to play screen
    JavatroManager.setScreen(JavatroView.getSelectCardsToPlayScreen());

  }

  @Override
  public String getDescription() {
    return "Play Cards";
  }

}
