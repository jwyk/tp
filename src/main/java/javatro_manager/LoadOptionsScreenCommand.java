package javatro_manager;

import javatro_view.JavatroView;
import javatro_view.OptionScreen;
import javatro_view.StartScreen;

public class LoadOptionsScreenCommand implements Command {

    public LoadOptionsScreenCommand() {

    }

    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroManager.getOptionScreen());
    }

    @Override
    public String getDescription() {
        return "Select Options";
    }

}
