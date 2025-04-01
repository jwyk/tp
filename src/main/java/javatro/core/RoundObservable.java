package javatro.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** Handles observer notifications for round state changes without direct Round dependencies. */
public class RoundObservable {
    /** The property change support for the observer pattern. */
    private final PropertyChangeSupport support;

    /**
     * Creates a new observable with the given source object.
     *
     * @param source The source object for property change events
     */
    public RoundObservable(Object source) {
        this.support = new PropertyChangeSupport(source);
    }

    /**
     * Registers an observer to listen for property changes.
     *
     * @param pcl The property change listener to register.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Fires property change events to notify observers of updated round variables.
     *
     * @param state The current state of the round
     * @param config The configuration of the round
     */
    public void updateRoundVariables(RoundState state, RoundConfig config) {
        support.firePropertyChange("blindScore", null, config.getBlindScore());
        support.firePropertyChange("remainingPlays", null, state.getRemainingPlays());
        support.firePropertyChange("remainingDiscards", null, state.getRemainingDiscards());
        support.firePropertyChange("roundName", null, config.getRoundName());
        support.firePropertyChange("roundDescription", null, config.getRoundDescription());
        support.firePropertyChange("holdingHand", null, state.getPlayerHandCards());
        support.firePropertyChange("currentScore", null, state.getCurrentScore());
    }
}
