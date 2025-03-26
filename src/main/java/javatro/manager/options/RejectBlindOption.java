package javatro.manager.options;

import javatro.core.Ante;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class RejectBlindOption implements Option {
    @Override
    public String getDescription() {
        return "Reject Blind";
    }

    @Override
    public void execute() throws JavatroException {
        if(JavatroCore.ante.getBlind() == Ante.Blind.SMALL_BLIND){
            JavatroCore.ante.setBlind(Ante.Blind.LARGE_BLIND);
            JavatroManager.setScreen(UI.getBlindScreen());
        }
        else if(JavatroCore.ante.getBlind() == Ante.Blind.LARGE_BLIND){
            JavatroCore.ante.setBlind(Ante.Blind.BOSS_BLIND);
            JavatroCore.currentRound.updateRoundVariables();
            JavatroManager.setScreen(UI.getGameScreen());
        }
    }
}
