package javatro.display.formatter.runselect;

import javatro.display.DeckArt;
import javatro.storage.utils.CardUtils;

public class DeckRenderer {

    public static String[] renderDeckArt(String deckName) {
        DeckArt deckArt = CardUtils.fromStorageKey(deckName);
        if (deckArt != null) {
            return deckArt.getArtLines();
        }
        return new String[] { "No art available." };
    }

    public static String renderDeckName(String deckName) {
        return "\u001B[3m" + deckName + "\u001B[0m"; // Italicized
    }
}
