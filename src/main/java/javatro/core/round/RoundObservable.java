package javatro.core.round;

import javatro.core.*;
import javatro.storage.DataParser;
import javatro.storage.Storage;
import javatro.storage.StorageManager;
import javatro.storage.utils.CardUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/** Handles observer notifications for round state changes without direct Round dependencies. */
class RoundObservable {
    /** The property change support for the observer pattern. */
    private final PropertyChangeSupport support;

    private final Storage storage = Storage.getStorageInstance();

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

        Storage storage = Storage.getStorageInstance();
        int runIndex = storage.getRunChosen() - 1;

        // Retrieve the run data
        ArrayList<String> runData = StorageManager.getInstance().getRunData(runIndex);
        assert runData != null : "Run data should not be null";

        // Update play counts, discards, and round score in the runData list
        runData.set(DataParser.HAND_INDEX, Integer.toString(state.getRemainingPlays()));
        runData.set(DataParser.DISCARD_INDEX, Integer.toString(state.getRemainingDiscards()));
        runData.set(DataParser.ROUND_SCORE_INDEX, Long.toString(state.getCurrentScore()));

        // Update Holding Hand Cards
        int cardIndex = DataParser.HOLDING_HAND_START_INDEX;
        for (Card c : state.getPlayerHandCards()) {
            runData.set(cardIndex, CardUtils.cardToString(c));
            cardIndex++;
        }

        // Update Rest of the Deck Cards
        ArrayList<Card> restOfTheCards = JavatroCore.currentRound.getDeck().getWholeDeck();

        for (int j = DataParser.START_OF_REST_OF_DECK;
                j < DataParser.START_OF_REST_OF_DECK + 44;
                j++) {
            int idx = j - DataParser.START_OF_REST_OF_DECK;
            if (idx >= restOfTheCards.size()) {
                runData.set(j, "-");
            } else {
                runData.set(j, CardUtils.cardToString(restOfTheCards.get(idx)));
            }
        }

        // Save all updated data at once
        StorageManager.getInstance().saveRunData(runIndex, runData);

        try {
            storage.updateSaveFile();
        } catch (JavatroException e) {
            System.out.println("Failed to Save To System");
        }
    }
}
