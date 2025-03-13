package javatro_manager;

import javatro_view.JavatroView;
import javatro_view.StartScreen;

public class LoadStartScreenCommand implements Command {

    public LoadStartScreenCommand() {

    }

    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroManager.getStartScreen());
    }

    @Override
    public String getDescription() {
        return "Main Menu";
    }

}
