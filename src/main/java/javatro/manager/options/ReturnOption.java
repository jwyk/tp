// @@author Markneoneo
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

/**
 * Enables navigation back to the previous screen in the view stack. Maintains screen history using
 * a last-in-first-out navigation model.
 */
public class ReturnOption implements Option {

    /** Reference to the previously displayed screen. */
    private Screen prev_screen;

    /**
     * {@inheritDoc}
     *
     * @return Constant description "Return To Previous Screen"
     */
    @Override
    public String getDescription() {
        return "Return To Previous Screen";
    }

    /**
     * {@inheritDoc} Restores the application display to the previous screen state.
     *
     * @throws JavatroException if screen history is unavailable
     */
    @Override
    public void execute() throws JavatroException {
        // Retrieve previous screen from navigation history
        prev_screen = UI.getPreviousScreen();
        assert prev_screen != null : "Screen history is empty";

        // Restore previous display state
        JavatroManager.setScreen(prev_screen);
    }
}
