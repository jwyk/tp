package javatro.manager.options;

import javatro.core.Ante;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;

public class RejectBlindOption implements Option {
    @Override
    public String getDescription() {
        return "Reject Blind";
    }

    @Override
    public void execute() throws JavatroException {
        if(JavatroManager.ante.getBlind() == Ante.Blind.SMALL_BLIND){
            JavatroManager.ante.setBlind(Ante.Blind.LARGE_BLIND);
            JavatroManager.setScreen(UI.getBlindScreen());
        }
        else if(JavatroManager.ante.getBlind() == Ante.Blind.LARGE_BLIND){
            JavatroManager.ante.setBlind(Ante.Blind.BOSS_BLIND);
            JavatroManager.setScreen(UI.getBlindScreen());
        }
        else{
            String title = "⚠\uFE0F " + UI.BOLD + "WARNING" + " ⚠\uFE0F" + UI.END;

            String[] lines = {
                    "Oops! you have to play the BOSS blind to proceed, no skipping! :D",
            };

            UI.printBorderedContent(title, List.of(lines));
            JavatroManager.setScreen(UI.getBlindScreen());
        }
    }
}
