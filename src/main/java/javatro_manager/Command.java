package javatro_manager;

import javatro_exception.JavatroException;

public interface Command {
    default String getDescription() {
        return "";
    }

    public void execute() throws JavatroException;
}
