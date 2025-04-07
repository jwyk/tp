// @@author Markneoneo
package javatro.display.screens;

import static javatro.display.UI.printANSI;

import javatro.audioplayer.AudioPlayer;
import javatro.core.JavatroException;
import javatro.manager.JavatroManager;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.HelpMenuOption;
import javatro.manager.options.LoadRunSelectOption;

/**
 * Initial application screen displaying main menu options and game logo.
 *
 * <p>Provides functionality for:
 *
 * <ul>
 *   <li>Displaying the Javatro logo
 *   <li>Presenting core game startup options
 *   <li>Handling basic application navigation
 * </ul>
 */
public class StartScreen extends Screen {

    /**
     * Constructs the start screen with default main menu options.
     *
     * @throws JavatroException if screen initialization fails
     */
    public StartScreen() throws JavatroException {
        super("MAIN MENU (RAISE YOUR PC VOL!!)");
        commandMap.add(new LoadRunSelectOption());
        commandMap.add(new HelpMenuOption());
        commandMap.add(new ExitGameOption());

        assert commandMap.size() == 3 : "StartScreen should have exactly 3 initial options";

    }

    /**
     * Displays the startup screen content including:
     *
     * <ul>
     *   <li>Javatro logo loaded from external file
     *   <li>Formatted menu options (handled by superclass)
     * </ul>
     */
    @Override
    public void displayScreen() {
        printANSI("javatro_logo.txt");

        AudioPlayer.getInstance().stopAudio();
        AudioPlayer.getInstance().playAudio("audioplayer/balatro_theme.wav");
    }

}
