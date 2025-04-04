package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.*;

import java.util.ArrayList;
import java.util.List;

import static javatro.display.UI.*;

public class RunSelectScreen extends Screen{



    private static int runNumber = 1;

    /**
     * Constructs a screen with specified options menu title.
     *
     * @throws JavatroException if optionsTitle is null or empty
     */
    public RunSelectScreen() throws JavatroException {
        super("Select Run To Load");
        super.commandMap.add(new SeeNextRunOption());
        super.commandMap.add(new SeePreviousRun());
        super.commandMap.add(new LoadJumpToRunScreenOption());
    }

    @Override
    public void displayScreen() {
        displayCurrentChosenRun();
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        RunSelectScreen.runNumber = runNumber;
    }


    public void displayCurrentChosenRun() {

        List<String> optionLines = new ArrayList<>();

        // Replace this with the actual split ANSI card art
        String[] cardArt = new String[]{
                "\u001B[38;5;255;48;5;15m▄\u001B[38;5;203;48;5;255m▄\u001B[38;5;210;48;5;255m▄\u001B[38;5;216;48;5;15m▄\u001B[38;5;210;48;5;255m▄▄\u001B[38;5;217;48;5;15m▄▄▄\u001B[38;5;210;48;5;255m▄▄\u001B[38;5;216;48;5;15m▄\u001B[38;5;210;48;5;255m▄\u001B[38;5;203;48;5;255m▄\u001B[38;5;255;48;5;15m▄\u001B[m",
                "\u001B[38;5;255;48;5;255m▄\u001B[38;5;216;48;5;210m▄\u001B[38;5;224;48;5;217m▄\u001B[38;5;217;48;5;224m▄\u001B[38;5;209;48;5;217m▄\u001B[38;5;217;48;5;217m▄\u001B[38;5;217;48;5;15m▄\u001B[38;5;15;48;5;224m▄\u001B[38;5;217;48;5;15m▄\u001B[38;5;217;48;5;217m▄\u001B[38;5;209;48;5;217m▄\u001B[38;5;217;48;5;224m▄\u001B[38;5;224;48;5;217m▄\u001B[38;5;216;48;5;210m▄\u001B[38;5;255;48;5;255m▄\u001B[m",
                "\u001B[48;5;255m \u001B[48;5;210m \u001B[38;5;216;48;5;224m▄\u001B[38;5;224;48;5;255m▄\u001B[38;5;210;48;5;203m▄\u001B[38;5;203;48;5;255m▄\u001B[38;5;210;48;5;203m▄\u001B[38;5;255;48;5;203m▄\u001B[38;5;210;48;5;203m▄\u001B[38;5;203;48;5;255m▄\u001B[38;5;210;48;5;203m▄\u001B[38;5;224;48;5;255m▄\u001B[38;5;216;48;5;224m▄\u001B[48;5;210m \u001B[48;5;255m \u001B[m",
                "\u001B[38;5;255;48;5;255m▄\u001B[38;5;203;48;5;216m▄\u001B[38;5;210;48;5;217m▄\u001B[38;5;210;48;5;224m▄\u001B[38;5;217;48;5;15m▄\u001B[38;5;15;48;5;210m▄\u001B[38;5;224;48;5;210m▄\u001B[38;5;217;48;5;210m▄\u001B[38;5;224;48;5;210m▄\u001B[38;5;15;48;5;210m▄\u001B[38;5;217;48;5;15m▄\u001B[38;5;210;48;5;224m▄\u001B[38;5;210;48;5;217m▄\u001B[38;5;203;48;5;216m▄\u001B[38;5;255;48;5;255m▄\u001B[m",
                "\u001B[38;5;255;48;5;255m▄\u001B[38;5;210;48;5;210m▄\u001B[38;5;224;48;5;217m▄\u001B[38;5;255;48;5;210m▄\u001B[38;5;15;48;5;223m▄\u001B[38;5;224;48;5;210m▄\u001B[38;5;210;48;5;255m▄\u001B[38;5;15;48;5;224m▄\u001B[38;5;210;48;5;255m▄\u001B[38;5;224;48;5;210m▄\u001B[38;5;15;48;5;223m▄\u001B[38;5;255;48;5;210m▄\u001B[38;5;224;48;5;217m▄\u001B[38;5;210;48;5;210m▄\u001B[38;5;255;48;5;255m▄\u001B[m",
                "\u001B[38;5;255;48;5;255m▄\u001B[38;5;210;48;5;210m▄\u001B[38;5;217;48;5;224m▄\u001B[38;5;210;48;5;255m▄\u001B[38;5;223;48;5;15m▄\u001B[38;5;210;48;5;224m▄\u001B[38;5;255;48;5;210m▄\u001B[38;5;224;48;5;15m▄\u001B[38;5;255;48;5;210m▄\u001B[38;5;210;48;5;224m▄\u001B[38;5;223;48;5;15m▄\u001B[38;5;210;48;5;255m▄\u001B[38;5;217;48;5;224m▄\u001B[38;5;210;48;5;210m▄\u001B[38;5;255;48;5;255m▄\u001B[m",
                "\u001B[38;5;255;48;5;255m▄\u001B[38;5;216;48;5;203m▄\u001B[38;5;217;48;5;210m▄\u001B[38;5;224;48;5;210m▄\u001B[38;5;15;48;5;217m▄\u001B[38;5;210;48;5;15m▄\u001B[38;5;210;48;5;224m▄\u001B[38;5;210;48;5;217m▄\u001B[38;5;210;48;5;224m▄\u001B[38;5;210;48;5;15m▄\u001B[38;5;15;48;5;217m▄\u001B[38;5;224;48;5;210m▄\u001B[38;5;217;48;5;210m▄\u001B[38;5;216;48;5;203m▄\u001B[38;5;255;48;5;255m▄\u001B[m",
                "\u001B[48;5;255m \u001B[48;5;210m \u001B[38;5;224;48;5;216m▄\u001B[38;5;255;48;5;224m▄\u001B[38;5;203;48;5;210m▄\u001B[38;5;255;48;5;203m▄\u001B[38;5;203;48;5;210m▄\u001B[38;5;203;48;5;255m▄\u001B[38;5;203;48;5;210m▄\u001B[38;5;255;48;5;203m▄\u001B[38;5;203;48;5;210m▄\u001B[38;5;255;48;5;224m▄\u001B[38;5;224;48;5;216m▄\u001B[48;5;210m \u001B[48;5;255m \u001B[m",
                "\u001B[38;5;255;48;5;255m▄\u001B[38;5;210;48;5;216m▄\u001B[38;5;217;48;5;224m▄\u001B[38;5;224;48;5;217m▄\u001B[38;5;217;48;5;209m▄\u001B[38;5;217;48;5;217m▄\u001B[38;5;15;48;5;217m▄\u001B[38;5;224;48;5;15m▄\u001B[38;5;15;48;5;217m▄\u001B[38;5;217;48;5;217m▄\u001B[38;5;217;48;5;209m▄\u001B[38;5;224;48;5;217m▄\u001B[38;5;217;48;5;224m▄\u001B[38;5;210;48;5;216m▄\u001B[38;5;255;48;5;255m▄\u001B[m",
                "\u001B[38;5;15;48;5;255m▄\u001B[38;5;255;48;5;203m▄\u001B[38;5;255;48;5;210m▄\u001B[38;5;15;48;5;216m▄\u001B[38;5;255;48;5;210m▄▄\u001B[38;5;15;48;5;217m▄▄▄\u001B[38;5;255;48;5;210m▄▄\u001B[38;5;15;48;5;216m▄\u001B[38;5;255;48;5;210m▄\u001B[38;5;255;48;5;203m▄\u001B[38;5;15;48;5;255m▄\u001B[m"
        };

        int screenSize = 11;
        String deckLabel = "\u001B[1;4mDECK\u001B[0m"; // Bold + Underlined
        String visibleCardLine = stripAnsi(cardArt[0]);
        int labelPadding = 78 + (visibleCardLine.length() - "DECK".length()) / 2;
        String paddedDeckLabel = String.format("%" + labelPadding + "s", deckLabel);
        optionLines.add(paddedDeckLabel);
        optionLines.add("");

        // Italicized deck name
        String italicDeckName = "\u001B[3mStandard Red Deck\u001B[0m";
        visibleCardLine = stripAnsi(cardArt[0]);
        int deckNamePadding = 95 + (visibleCardLine.length() - "Standard Red Deck".length()) / 2;
        String paddedDeckName = String.format("%" + deckNamePadding + "s", italicDeckName);


        for (int i = 0; i < screenSize; i++) {
            String leftText = "";
            if (i == 2) leftText = "Round: 0";
            else if (i == 3) leftText = "Total Rounds: 10";
            else if (i == 4) leftText = "Ante: 1";
            else if (i == 5) leftText = "Chips: $150";
            else if (i == 6) leftText = "Wins: 3 | Losses: 2";
            else if (i == 7) leftText = "------------------------";
            else if (i == 8) leftText = "Last Action: Drew 1 card";


            String rightAnsi = (i < cardArt.length) ? cardArt[i] : "";
            // Pad leftText to align with ANSI
            String paddedLeft = String.format("%-70s", leftText);

            optionLines.add(paddedLeft + rightAnsi);
        }

        optionLines.add(paddedDeckName);

        printBorderedContent("RUN #" + runNumber, optionLines);
        System.out.println("\n");

    }

    String stripAnsi(String input) {
        return input.replaceAll("\\u001B\\[[;\\d]*m", "");
    }

}
