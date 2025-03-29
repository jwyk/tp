package javatro.manager.options;

import static javatro.manager.JavatroManager.jc;

import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.core.PlanetCard;
import javatro.core.PokerHand;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;
import java.util.Random;

public class NextRoundOption implements Option {
    @Override
    public String getDescription() {
        return "Start Next Round";
    }

    @Override
    public void execute() throws JavatroException {

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
                    + "Mult",
        };

        UI.printBorderedContent(title, List.of(lines));
        jc.nextRound();
        JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
        JavatroCore.currentRound.updateRoundVariables();
        javatro.display.UI.getGameScreen().restoreGameCommands();
        JavatroManager.setScreen(UI.getGameScreen());
    }
}
