package javatro.manager.options;

import static javatro.manager.JavatroManager.jc;

import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class NextRoundOption implements Option {
    @Override
    public String getDescription() {
        return "Start Next Round";
    }

    @Override
    public void execute() throws JavatroException {
        jc.nextRound();
        jc.beginGame();
        JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
        JavatroCore.currentRound.updateRoundVariables();
        javatro.display.UI.getGameScreen().restoreGameCommands();
        JavatroManager.setScreen(UI.getGameScreen());
    }
}
