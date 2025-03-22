package Javatro.UI.Screens;

import Javatro.Manager.Options.HelpHowToPlayOption;
import Javatro.Manager.Options.HelpIntroOption;
import Javatro.Manager.Options.HelpRulesOption;
import Javatro.Manager.Options.HelpTipsOption;
import Javatro.Manager.Options.LoadStartScreenOption;

/**
 * The HelpScreen class represents the help menu screen in Javatro. It provides various help options
 * such as game introduction, rules, how to play, tips, and an option to return to the start screen.
 */
public class HelpScreen extends Screen {

    /** Constructs a new HelpScreen and initializes the available help commands. */
    public HelpScreen() {
        super("HELP MENU");
        commandMap.add(new HelpIntroOption());
        commandMap.add(new HelpRulesOption());
        commandMap.add(new HelpHowToPlayOption());
        commandMap.add(new HelpTipsOption());
        commandMap.add(new LoadStartScreenOption());
    }

    /** Displays the help menu screen with a welcome message. */
    @Override
    public void displayScreen() {
        System.out.println("Welcome to the Help Menu");
    }
}
