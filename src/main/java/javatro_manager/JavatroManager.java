/**
 * The {@code JavatroManager} class serves as the main controller (manager) of the game,
 * coordinating interactions between the model ({@code JavatroCore}) and the view ({@code
 * JavatroView}). It listens for property changes and updates the game state accordingly.
 */
package javatro_manager;

import javatro_core.JavatroCore;

import javatro_exception.JavatroException;

import javatro_view.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Manages the interaction between the game model and the user interface. It listens for user inputs
 * and updates the game state dynamically.
 */
public class JavatroManager implements PropertyChangeListener {

    /** The main view responsible for rendering the user interface. */
    private static JavatroView jv;
    /** The main model responsible for handling game logic. */
    private static JavatroCore jc;
    /** Stores the last recorded user input. */
    private static int userInput;

    /**
     * Constructs a {@code JavatroManager} and registers it as an observer to the view.
     *
     * @param jv The main view of the game.
     * @param jc The main model of the game.
     */
    public JavatroManager(JavatroView jv, JavatroCore jc) {
        JavatroManager.jv = jv;
        JavatroManager.jc = jc;
        jv.addPropertyChangeListener(this); // Register as an observer
    }

    /**
     * Changes the currently displayed screen.
     *
     * @param destinationScreen The new screen to be displayed.
     */
    public static void setScreen(Screen destinationScreen) {
        jv.setCurrentScreen(destinationScreen);
    }

    /**
     * Begins the game by initializing the game model and registering necessary observers.
     *
     * @throws JavatroException If an error occurs during game initialization.
     */
    public static void beginGame() throws JavatroException {
        jc.beginGame();
        JavatroCore.currentRound.addPropertyChangeListener(JavatroView.getGameScreen());
        // Fire property changes here
        JavatroCore.currentRound.updateRoundVariables();
    }

    /**
     * Handles property change events from the view. If the property change corresponds to user
     * input, it executes the appropriate command.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("userInput")) {
            // Execute the respective command
            try {
                jv.getCurrentScreen().getCommand((int) evt.getNewValue() - 1).execute();
            } catch (JavatroException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
