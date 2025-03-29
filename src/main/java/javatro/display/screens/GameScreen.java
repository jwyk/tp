package javatro.display.screens;

import javatro.core.Card;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.options.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static javatro.display.UI.*;

public class GameScreen extends Screen implements PropertyChangeListener {

    public static int roundOver = 0;
    private static int blindScore = 0;
    private static long roundScore = 0;
    private static int handsLeft = 0;
    private static int discardsLeft = 0;
    private static List<Card> holdingHand;

    private String roundName = "";
    private String roundDescription = "";

    public GameScreen() throws JavatroException {
        super("GAME MENU");
        commandMap.add(new PlayCardOption());
        commandMap.add(new DiscardCardOption());
        commandMap.add(new MainMenuOption());
        commandMap.add(new ExitGameOption());
    }

    public void restoreGameCommands() {
        commandMap.clear();
        commandMap.add(new PlayCardOption());
        commandMap.add(new DiscardCardOption());
        commandMap.add(new DeckOption());
        commandMap.add(new MainMenuOption());
        commandMap.add(new ExitGameOption());
    }

    private void printRoundOver() {
        String title = "::: " + BOLD + "Round Ended" + " :::" + END;
        String message = roundOver == 1 ? "YOU WON!!!!" : "YOU LOST!!!!";
        printBorderedContent(title, List.of("", message, ""));
    }

    @Override
    public void displayScreen() {
        clearScreen();

        if (roundOver != 0) {
            printRoundOver();
        }

        List<String> content = new ArrayList<>();

        // Round Information
        content.add(BOLD + PURPLE + roundName + END);
        content.add(ITALICS + roundDescription + END);
        content.add(GREEN + "Blind Score: " + blindScore + END);
        content.add(BLUE + "Round Score: " + roundScore + END);
        content.add("");

        // Jokers Display
        content.add(BOLD + "Jokers" + END);
        addCardRow(Collections.nCopies(5, "X"), content);
        content.add("");

        // Player's Hand
        content.add(BOLD + "Your Hand" + END);
        List<String> cardSymbols = holdingHand.stream()
                .map(Card::getSimplified)
                .collect(Collectors.toList());
        addCardRow(cardSymbols, content);
        content.add("");

        // Game Stats
        content.add(YELLOW + "Hands Left: " + handsLeft + END + " | " +
                RED + "Discards Left: " + discardsLeft + END);
        content.add(PURPLE + "Cash: $ -" + END + " | " +
                ORANGE + "Ante: " + JavatroCore.getAnte().getAnteCount() + "/8" + END);
        content.add(BOLD + "Current Round: " + JavatroCore.getRoundCount() + END);

        printBorderedContent("Game Screen", content);
    }

    private void addCardRow(List<String> cardTexts, List<String> content) {
        List<String> top = new ArrayList<>();
        List<String> mid = new ArrayList<>();
        List<String> bottom = new ArrayList<>();

        for (String text : cardTexts) {
            String topLine = BLACK_B + TOP_LEFT + String.valueOf(HORIZONTAL).repeat(5) + TOP_RIGHT + END;
            String midLine = BLACK_B + VERTICAL + centerCardText(text, 5) + VERTICAL + END;
            String bottomLine = BLACK_B + BOTTOM_LEFT + String.valueOf(HORIZONTAL).repeat(5) + BOTTOM_RIGHT + END;

            top.add(topLine);
            mid.add(midLine);
            bottom.add(bottomLine);
        }

        content.add(String.join("  ", top));
        content.add(String.join("  ", mid));
        content.add(String.join("  ", bottom));
    }

    private String centerCardText(String text, int width) {
        int displayLength = getDisplayLength(text);
        int padding = (width - displayLength) / 2;
        int extra = (width - displayLength) % 2;
        return " ".repeat(padding) + text + " ".repeat(padding + extra);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();

        Map<String, Consumer<Object>> handlers = new HashMap<>();
        handlers.put("roundName", v -> roundName = v.toString());
        handlers.put("remainingPlays", v -> handsLeft = (Integer) v);
        handlers.put("remainingDiscards", v -> discardsLeft = (Integer) v);
        handlers.put("currentScore", v -> roundScore = (Long) v);
        handlers.put("roundDescription", v -> roundDescription = v.toString());
        handlers.put("blindScore", v -> blindScore = (Integer) v);
        handlers.put("holdingHand", v -> {
            List<?> rawList = (List<?>) v;
            holdingHand = rawList.stream()
                    .filter(Card.class::isInstance)
                    .map(Card.class::cast)
                    .collect(Collectors.toList());
        });
        handlers.put("roundComplete", v -> {
            roundOver = (Integer) v;
            if (roundOver == 1) {
                commandMap.clear();
                commandMap.add(new NextRoundOption());
                commandMap.add(new ExitGameOption());
            } else if (roundOver == -1) {
                commandMap.clear();
                commandMap.add(new MainMenuOption());
                commandMap.add(new ExitGameOption());
            }
        });

        handlers.getOrDefault(propertyName, val -> {}).accept(newValue);
    }
}