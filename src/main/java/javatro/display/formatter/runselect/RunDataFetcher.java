package javatro.display.formatter.runselect;

import javatro.storage.DataParser;
import javatro.storage.Storage;

public class RunDataFetcher {

    private final Storage storage;
    private final int runIndex;

    public RunDataFetcher(int runIndex) {
        this.storage = Storage.getStorageInstance();
        this.runIndex = runIndex;
    }

    public String getDeckName() {
        return storage.getValue(runIndex, DataParser.DECK_INDEX);
    }

    public String getRoundNumber() {
        return storage.getValue(runIndex, DataParser.ROUND_NUMBER_INDEX);
    }

    public String getRoundScore() {
        return storage.getValue(runIndex, DataParser.ROUND_SCORE_INDEX);
    }

    public String getHandIndex() {
        return storage.getValue(runIndex, DataParser.HAND_INDEX);
    }

    public String getDiscardIndex() {
        return storage.getValue(runIndex, DataParser.DISCARD_INDEX);
    }

    public String getWins() {
        return storage.getValue(runIndex, DataParser.WINS_INDEX);
    }

    public String getLosses() {
        return storage.getValue(runIndex, DataParser.LOSSES_INDEX);
    }
}
