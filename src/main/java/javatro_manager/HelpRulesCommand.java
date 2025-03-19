package javatro_manager;

import javatro_view.JavatroView;

public class HelpRulesCommand  implements Command {

    /**
     * The HelpRulesCommand class provides the rules for playing Javatro.
     * This command is executed when the player requests game rules.
     */
    public HelpRulesCommand() {}

    /**
     * Executes the command to display the rules of the game.
     */
    @Override
    public void execute() {
        System.out.println("\n=== Javatro Rules ===");
        System.out.println("1. Start with a basic deck of cards.");
        System.out.println("2. Each turn, draw cards and form poker hands to attack or defend.");
        System.out.println("3. Earn new cards after each battle and upgrade your deck.");
        System.out.println("4. If your health reaches zero, the run ends—try again with a new strategy!");
        System.out.println("5. Different enemies and challenges appear in each run.");
        System.out.println("\nRemember: Careful deck management is key to survival!");

        JavatroManager.setScreen(JavatroView.getHelpScreen());
    }

    /**
     * Returns a description of this command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getDescription() {
        return "Game Rules";
    }
}
