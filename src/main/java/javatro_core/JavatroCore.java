package javatro_core;

import Javatro.Deck;
import Javatro.JavatroException;
import Javatro.Round;
import Javatro.State;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

//Main Model class
public class JavatroCore {
    private static Round currentRound; //Instance of round class
    private static State gameState;
    private static Deck deck;

    private PropertyChangeSupport support = new PropertyChangeSupport(this); // Observable

    // Register an observer (Controller)
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    //Start a round
    //Return round later on?
    public void startRound(String roundName, String roundDescription) {
        Round round = new Round()
    }

    public static int getBlindScore() {
        return gameState.getBlindScore();
    }

    public static int getPlaysLeft() {
        return round.getRemainingPlays();
    }

    public static int getDiscardsLeft() {
        return round.getRemainingDiscards();
    }

    public static String getRoundName() {
        return round.getRoundName();
    }

    public static String getRoundDescription() {
        return round.getRoundDescription();
    }

}
