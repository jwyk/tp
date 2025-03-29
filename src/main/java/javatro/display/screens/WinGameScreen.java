package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.MainMenuOption;

public class WinGameScreen extends Screen {
    /**
     * Constructs a screen with the specified options title.
     *
     * @throws JavatroException if the options title is null or empty
     */
    public WinGameScreen() throws JavatroException {
        super("win");
        commandMap.add(new MainMenuOption());
        commandMap.add(new ExitGameOption());
    }

    /**
     * Displays the screen content. This method must be implemented by subclasses to define the
     * specific behavior and layout of the screen.
     */
    @Override
    public void displayScreen() {

    }

//    "You Aced it!"
//            "You dealt with that pretty well!"
//            "Looks like you weren't bluffing!"
//            "Too bad these chips are all virtual..."
//            "Looks like I've taught you well!"
//            "You made some heads up plays!"
//            "Good thing I didn't bet against you!"
}
