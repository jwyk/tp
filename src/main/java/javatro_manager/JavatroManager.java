package javatro_manager;

import javatro_core.JavatroCore;

import javatro_exception.JavatroException;

import javatro_view.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Main Controller (Manager) Class
public class JavatroManager implements PropertyChangeListener {

    private static JavatroView jv; // Main View
    private static JavatroCore jc; // Main Model

    private static int userInput;

    public JavatroManager(JavatroView jv, JavatroCore jc) {
        JavatroManager.jv = jv;
        JavatroManager.jc = jc;
        jv.addPropertyChangeListener(this); // Register as an observer
    }

    // Changes the screen to display
    public static void setScreen(Screen destinationScreen) {
        jv.setCurrentScreen(destinationScreen);
    }

    public static void beginGame() throws JavatroException {
        jc.beginGame();
        JavatroCore.currentRound.addPropertyChangeListener(JavatroView.getGameScreen());
        // Fire property changes here
        JavatroCore.currentRound.updateRoundVariables();
    }

    // Called when the view says there is a change
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
