package javatro_manager;

import static javatro_view.GameScreen.roundOver;

import javatro_view.GameScreen;
import javatro_view.JavatroView;

public class LoadStartScreenCommand implements Command {

    public LoadStartScreenCommand() {}

    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroView.getStartScreen());
    }

    @Override
    public String getDescription() {
        return "Main Menu";
    }
}
