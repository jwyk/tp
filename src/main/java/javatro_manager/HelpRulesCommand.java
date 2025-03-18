package javatro_manager;

import javatro_view.JavatroView;

public class HelpRulesCommand  implements Command {



    public HelpRulesCommand() {}

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

    @Override
    public String getDescription() {
        return "Game Rules";
    }
}
