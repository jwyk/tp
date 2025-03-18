package javatro_manager;

import javatro_view.JavatroView;

public class HelpCommand implements Command{

    public HelpCommand() {}

    @Override
    public void execute() {
        JavatroManager.setScreen(JavatroView.getHelpScreen());
    }

    @Override
    public String getDescription() {
        return "Select Help";
    }
}
