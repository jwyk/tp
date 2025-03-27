package javatro.manager.options;

import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;

public class AcceptBlindOption implements Option {
    @Override
    public String getDescription() {
        return "Accept Blind";
    }


    @Override
    public void execute() throws JavatroException {}
}
