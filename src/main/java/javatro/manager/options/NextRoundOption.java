package javatro.manager.options;

import static javatro.manager.JavatroManager.jc;

import javatro.core.Ante;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.core.PlanetCard;
import javatro.core.PokerHand;
import javatro.core.jokers.Joker;
import javatro.core.jokers.JokerFactory;
import javatro.display.UI;
import javatro.manager.JavatroManager;

import java.util.List;
import java.util.Random;

public class NextRoundOption implements Option {

    @Override
    public String getDescription() {
        return "Start Next Round";
    }

    /**
     * Executes the command to move on to the next round. The command will give the player a {@code
     * PlanetCard} or a {@code Joker}, depending on the Blind won.
     */
    @Override
    public void execute() throws JavatroException {
        // Instantiate next round
        jc.nextRound();
        JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
        JavatroCore.currentRound.updateRoundVariables();
        JavatroManager.setScreen(UI.getGameScreen());
    }
}
