package javatro_view;

import javatro_manager.*;

/**
 * The HelpScreen class represents the help menu screen in Javatro. It provides various help options
 * such as game introduction, rules, how to play, tips, and an option to return to the start screen.
 */
public class HelpScreen extends Screen {

    /** Constructs a new HelpScreen and initializes the available help commands. */
    public HelpScreen() {
        super("HELP MENU");
        commandMap.add(new HelpIntroCommand());
        commandMap.add(new HelpRulesCommand());
        commandMap.add(new HelpHowToPlayCommand());
        commandMap.add(new HelpTipsCommand());
        commandMap.add(new LoadStartScreenCommand());
    }

    /** Displays the help menu screen with a welcome message. */
    @Override
    public void displayScreen() {
        System.out.println("Welcome to the hHelp Menu");
    }
}
