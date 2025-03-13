package javatro_manager;

import javatro_view.GameScreen;
import javatro_view.JavatroView;

public class LoadGameScreenCommand implements Command {


    public LoadGameScreenCommand() {
    }

    @Override
    public void execute() {
        //Update the main screen to show game screen
        JavatroManager.setScreen(JavatroManager.getGameScreen());
    }

    @Override
    public String getDescription() {
        return "Start Game";
    }

}
