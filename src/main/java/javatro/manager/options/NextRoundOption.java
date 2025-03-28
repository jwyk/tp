package javatro.manager.options;

import javatro.core.Deck;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

//@author swethaiscool
public class NextRoundOption implements Option  {
    @Override
    public String getDescription() {
        return "Start Next Round";
    }

    @Override
    public void execute() throws JavatroException {
        JavatroManager.ante.nextRound();
        JavatroManager.roundCount++;
        JavatroManager.jc.beginGame();
        JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
        JavatroCore.currentRound.updateRoundVariables();
        javatro.display.UI.getGameScreen().restoreGameCommands();
        JavatroManager.setScreen(UI.getGameScreen());
    }
}