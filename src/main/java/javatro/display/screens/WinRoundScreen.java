package javatro.display.screens;

import javatro.core.*;
import javatro.core.jokers.Joker;
import javatro.core.jokers.JokerFactory;
import javatro.display.UI;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.NextRoundOption;

import java.util.List;
import java.util.Random;

public class WinRoundScreen extends Screen {

    /**
     * Constructs a screen with the specified options title.
     *
     * @throws JavatroException if the options title is null or empty
     */
    public WinRoundScreen() throws JavatroException {
        super("win");
        commandMap.add(new NextRoundOption());
        commandMap.add(new ExitGameOption());
    }

    /**
     * Displays the screen content. This method must be implemented by subclasses to define the
     * specific behavior and layout of the screen.
     */
    @Override
    public void displayScreen() {

        if (JavatroCore.getAnte().getBlind() == Ante.Blind.BOSS_BLIND) {
            // Give the player a random joker card, and move to next round.
            Joker randomJoker = JokerFactory.createRandomJoker();
            String title = "Won the round! Got a free " + randomJoker.getName() + " card.";
            String[] lines = {randomJoker.getName() + ": " + randomJoker.getDescription() + "."};
            try {
                JavatroCore.heldJokers.add(randomJoker);
                UI.printBorderedContent(title, List.of(lines));
            } catch (JavatroException e) {
                lines = new String[] {"Can't add any more Jokers, or limit will be exceeded."};
                UI.printBorderedContent(title, List.of(lines));
            }

        } else {
            // Give the player a random free planet card, and move to next round.
            PokerHand.HandType[] handTypes = PokerHand.HandType.values();
            Random random = new Random();
            PokerHand.HandType randomPlanetCard = handTypes[random.nextInt(handTypes.length)];
            PlanetCard.getForHand(randomPlanetCard).apply();
            String title =
                    "Won the round! Got a free "
                            + PlanetCard.getForHand(randomPlanetCard).getName()
                            + " card.";

            String[] lines = {
                    randomPlanetCard.getHandName()
                            + ": +"
                            + PlanetCard.getChipIncrement(randomPlanetCard)
                            + " Chips"
                            + ", +"
                            + PlanetCard.getMultiIncrement(randomPlanetCard)
                            + " Mult.",
            };
            UI.printBorderedContent(title, List.of(lines));
        }
    }
}
