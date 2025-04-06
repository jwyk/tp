package javatro.core.round;

import javatro.core.*;
import javatro.storage.Storage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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

        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.HAND_INDEX,
                Integer.toString(state.getRemainingPlays()));
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.DISCARD_INDEX,
                Integer.toString(state.getRemainingDiscards()));
        storage.setValue(
                storage.getRunChosen() - 1,
                Storage.ROUND_SCORE_INDEX,
                Long.toString(state.getCurrentScore()));

        int i = Storage.HOLDING_HAND_START_INDEX;
        for (Card c : state.getPlayerHandCards()) {
            storage.setValue(storage.getRunChosen() - 1, i, Storage.cardToString(c));
            i = i + 1;
        }

        // Check whether player has won or lost
        if (JavatroCore.currentRound.isRoundOver()
                || JavatroCore.currentRound.getRemainingPlays() == 0) {
            if (JavatroCore.currentRound.isWon()) {
                Ante.Blind nextBlind = JavatroCore.getAnte().getNextBlind();
                int anteCount = JavatroCore.getAnte().getAnteCount();
                if (nextBlind == Ante.Blind.SMALL_BLIND
                        && JavatroCore.getAnte().getBlind() == Ante.Blind.BOSS_BLIND) {
                    anteCount = anteCount + 1;
                }

                storage.setValue(
                        storage.getRunChosen() - 1, Storage.HAND_INDEX, Integer.toString(-1));
                storage.setValue(
                        storage.getRunChosen() - 1, Storage.DISCARD_INDEX, Integer.toString(-1));
                storage.setValue(
                        storage.getRunChosen() - 1, Storage.ROUND_SCORE_INDEX, Long.toString(0));
                storage.setValue(
                        storage.getRunChosen() - 1,
                        Storage.ROUND_NUMBER_INDEX,
                        Long.toString(JavatroCore.getRoundCount() + 1));
                storage.setValue(
                        storage.getRunChosen() - 1,
                        Storage.ANTE_NUMBER_INDEX,
                        String.valueOf(anteCount)); // Update Ante Count
                storage.setValue(
                        storage.getRunChosen() - 1,
                        Storage.BLIND_INDEX,
                        nextBlind.getName()); // Update Blind

                i = Storage.HOLDING_HAND_START_INDEX;
                for (Card c : state.getPlayerHandCards()) {
                    storage.setValue(storage.getRunChosen() - 1, i, "-");
                    i = i + 1;
                }

                int currentWin =
                        Integer.parseInt(
                                Storage.getStorageInstance()
                                        .getValue(
                                                Storage.getStorageInstance().getNumberOfRuns() - 1,
                                                Storage.WINS_INDEX));
                Storage.getStorageInstance()
                        .setValue(
                                Storage.getStorageInstance().getRunChosen() - 1,
                                Storage.WINS_INDEX,
                                String.valueOf(currentWin + 1));

            } else if (JavatroCore.currentRound.isLost()) {
                Ante.Blind currentBlind = JavatroCore.getAnte().getBlind();
                int anteCount = JavatroCore.getAnte().getAnteCount();

                storage.setValue(
                        storage.getRunChosen() - 1, Storage.HAND_INDEX, Integer.toString(-1));
                storage.setValue(
                        storage.getRunChosen() - 1, Storage.DISCARD_INDEX, Integer.toString(-1));
                storage.setValue(
                        storage.getRunChosen() - 1, Storage.ROUND_SCORE_INDEX, Long.toString(0));
                storage.setValue(
                        storage.getRunChosen() - 1,
                        Storage.ROUND_NUMBER_INDEX,
                        Long.toString(JavatroCore.getRoundCount()));
                storage.setValue(
                        storage.getRunChosen() - 1,
                        Storage.ANTE_NUMBER_INDEX,
                        String.valueOf(anteCount)); // Update Ante Count
                storage.setValue(
                        storage.getRunChosen() - 1,
                        Storage.BLIND_INDEX,
                        currentBlind.getName()); // Update Blind

                i = Storage.HOLDING_HAND_START_INDEX;
                for (Card c : state.getPlayerHandCards()) {
                    storage.setValue(storage.getRunChosen() - 1, i, "-");
                    i = i + 1;
                }

                int currentLose =
                        Integer.parseInt(
                                Storage.getStorageInstance()
                                        .getValue(
                                                Storage.getStorageInstance().getRunChosen() - 1,
                                                Storage.LOSSES_INDEX));
                Storage.getStorageInstance()
                        .setValue(
                                Storage.getStorageInstance().getRunChosen() - 1,
                                Storage.LOSSES_INDEX,
                                String.valueOf(currentLose + 1));
            }
        }

        try {
            storage
                    .updateSaveFile(); // Update save file at each run with the updated hands,
                                       // discards and round score
        } catch (JavatroException e) {
            System.out.println("Failed to Save To System");
        }
    }
}
