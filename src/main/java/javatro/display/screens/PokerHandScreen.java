package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.core.PokerHand;
import javatro.manager.options.ReturnOption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javatro.display.UI.printBorderedContent;

public class PokerHandScreen extends Screen {

    private static final String TITLE =
            "LEVEL      HAND        CHIPS × MULTI       PLAYED";

    private final List<PokerHand> pokerHands;

    public PokerHandScreen() throws JavatroException {
        super("Javatro Poker Hands");
        super.commandMap.add(new ReturnOption());

        // Initialize with all hand types
        this.pokerHands = Stream.of(PokerHand.HandType.values())
                .map(PokerHand::new)
                .collect(Collectors.toList());
    }

    @Override
    public void displayScreen() {
        List<String> handDisplays = pokerHands.stream()
                .map(PokerHand::toDisplayString)
                .collect(Collectors.toList());
        printBorderedContent(TITLE, handDisplays);
    }

    /**
     * Updates the played count for a specific hand type.
     *
     * @param handType The hand type to update
     */
    public void incrementPlayed(PokerHand.HandType handType) {
        pokerHands.replaceAll(hand ->
                hand.handType() == handType ? hand.incrementPlayed() : hand);
    }

    /**
     * Updates the level for a specific hand type.
     *
     * @param handType The hand type to update
     * @param newLevel The new level to set
     */
    public void setLevel(PokerHand.HandType handType, int newLevel) {
        pokerHands.replaceAll(hand ->
                hand.handType() == handType ? hand.withLevel(newLevel) : hand);
    }

    /**
     * Gets a specific poker hand by type.
     *
     * @param handType The hand type to retrieve
     * @return The PokerHand instance
     */
    public PokerHand getHand(PokerHand.HandType handType) {
        return pokerHands.stream()
                .filter(hand -> hand.handType() == handType)
                .findFirst()
                .orElseThrow();
    }
}