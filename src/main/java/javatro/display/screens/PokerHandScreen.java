package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.core.PokerHand;
import javatro.core.PlanetCard;
import javatro.manager.options.ReturnOption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javatro.display.UI.printBorderedContent;

public class PokerHandScreen extends Screen {

    // %-X = X characters width
    private static final String TITLE =
            String.format("%-8s  %-16s  %-12s  %-8s", "LEVEL", "POKER HAND", "CHIPS × MULTI", "PLAYED");

    private final List<PokerHand> pokerHands;

    public PokerHandScreen() throws JavatroException {
        super("Javatro Poker Hands");
        super.commandMap.add(new ReturnOption());

        this.pokerHands = Stream.of(PokerHand.HandType.values())
                .map(PokerHand::new)
                .collect(Collectors.toList());
    }

    @Override
    public void displayScreen() {
        List<String> handDisplays = pokerHands.stream()
                .map(hand -> {
                    PokerHand.HandType type = hand.handType();
                    return String.format("%-8d  %-16s  %-4d × %-6d  %-8d",
                            PlanetCard.getLevel(type),
                            type.getHandName(),
                            hand.getChips(),
                            hand.getMultiplier(),
                            hand.playCount());
                })
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
                hand.handType() == handType ? hand.incrementPlayed() : hand
        );
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