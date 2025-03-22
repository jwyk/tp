package Javatro.Manager.Options;


/**
 * The {@code ExitGameOption} class represents a command that terminates the game. When executed,
 * it prints a farewell message and exits the application.
 */
public class ExitGameOption implements Option {

    /**
     * Provides a brief description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Exit Game";
    }

    /**
     * Executes the exit game command, displaying a farewell message and terminating the
     * application.
     */
    @Override
    public void execute() {
        System.out.println("================================================");
        System.out.println(" GGGG    OOO   OOO   DDDD    BBBB   Y   Y  EEEEE ");
        System.out.println("G       O   O O   O  D   D   B   B   Y Y   E     ");
        System.out.println("G  GG   O   O O   O  D   D   BBBB     Y    EEEE  ");
        System.out.println("G   G   O   O O   O  D   D   B   B    Y    E     ");
        System.out.println(" GGGG    OOO   OOO   DDDD    BBBB     Y    EEEEE ");
        System.out.println("================================================");
        System.out.println("      Thanks for playing! See you next time!     ");
        System.out.println("================================================");
        System.exit(0);
    }
}
