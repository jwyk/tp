package javatro_manager;

import javatro_exception.JavatroException;

/**
 * The {@code Command} interface represents an executable action within the game. It follows the
 * Command design pattern, allowing encapsulation of actions that can be executed dynamically.
 */
public interface Command {

    /**
     * Retrieves a description of the command. This method has a default implementation returning an
     * empty string.
     *
     * @return A string representing the command description.
     */
    default String getDescription() {
        return "";
    }

    /**
     * Executes the command. Implementing classes must define specific behavior.
     *
     * @throws JavatroException If an error occurs during execution.
     */
    void execute() throws JavatroException;
}
