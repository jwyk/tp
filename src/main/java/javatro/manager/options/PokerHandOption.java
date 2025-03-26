package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class PokerHandOption implements Option {

    @Override
    public String getDescription() {
        return "View Poker Hands";
    }

    @Override
    public void execute() throws JavatroException {
        JavatroManager.setScreen(UI.getPokerHandScreen());
    }
}