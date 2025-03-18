package javatro_view;

import javatro_manager.*;

public class HelpScreen extends Screen{

    public HelpScreen() {
        super("HELP MENU");
        commandMap.add(new HelpIntroCommand());
        commandMap.add(new HelpRulesCommand());
        commandMap.add(new HelpHowToPlayCommand());
        commandMap.add(new HelpTipsCommand());
        commandMap.add(new LoadStartScreenCommand());
    }

    @Override
    public void displayScreen() {
        System.out.println("Welcome to help menu");
    }
}
