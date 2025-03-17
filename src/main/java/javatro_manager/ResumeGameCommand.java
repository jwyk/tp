package javatro_manager;

import javatro_exception.JavatroException;

import javatro_view.JavatroView;

public class ResumeGameCommand implements Command {

    @Override
    public String getDescription() {
        return "Return To Game";
    }

    @Override
    public void execute() throws JavatroException {
        // Update the main screen to show game screen
        JavatroManager.setScreen(JavatroView.getGameScreen());
    }
}
