/**
 * The {@code ViewRunOption} class represents a command that allows users to select and view a
 * specific saved run. When executed, it triggers the display of the associated run's details.
 *
 * <p>This class implements the {@link Option} interface and is typically used within screens that
 * list available saved runs, such as {@link javatro.display.screens.RunListScreen}.
 */
package javatro.manager.options;

import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.RunSelectScreen;
import javatro.manager.JavatroManager;

/**
 * A command that loads the run selection screen for a specific saved run.
 *
 * <p>This command is dynamically created for each saved run and added to a command map. When
 * executed, it transitions the application to the corresponding run's detail screen.
 */
public class ViewRunOption implements Option {

    /** The run number associated with this option. */
    private int runNumber = 0;

    /**
     * Sets the run number for this option.
     *
     * @param runNumber The run number to associate with this option.
     */
    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    /**
     * Returns a description of this option, which includes the run number it represents.
     *
     * @return A string describing this option, formatted as "View Run #<runNumber>".
     */
    @Override
    public String getDescription() {
        return "View Run #" + runNumber;
    }

    /**
     * Executes the command to change the screen to the run selection screen.
     *
     * <p>This method triggers the following actions:
     *
     * <ul>
     *   <li>Sets the run number of the {@link UI#getRunSelectScreen()} instance.
     * </ul>
     *
     * @throws JavatroException if there is an error during screen transition.
     */
    @Override
    public void execute() throws JavatroException {
        RunSelectScreen.setRunNumber(runNumber);
        JavatroManager.setScreen(UI.getRunSelectScreen());
    }

    public int getRunNumber() {
        return runNumber;
    }
}
