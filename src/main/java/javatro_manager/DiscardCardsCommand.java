package javatro_manager;

import javatro_exception.JavatroException;

import javatro_view.JavatroView;

/**
 * The {@code DiscardCardsCommand} class represents a command that allows the player to discard
 * selected cards during the game. It updates the game screen to display the card selection
 * interface for discarding cards.
 */
public class DiscardCardsCommand implements Command {

    /**
     * Executes the discard cards command, updating the game screen to the "Select Cards to Discard"
     * interface.
     *
     * @throws JavatroException If an error occurs during execution.
     */
    @Override
    public void execute() throws JavatroException {
        // Update the main screen to show select cards to discard screen
        JavatroManager.setScreen(JavatroView.getSelectCardsToDiscardScreen());
    }

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Discard Cards";
    }
}
