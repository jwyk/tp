package javatro.display.screens;

import javatro.core.JavatroException;
import javatro.manager.options.*;
import javatro.storage.Storage;

import java.util.ArrayList;
import java.util.List;

import static javatro.display.UI.*;
import static javatro.display.ansi.DeckArt.*;

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
        String[] cardArt;
        String cardDesc = "";
        if(Storage.getStorageInstance().getValue(runNumber-1,8).charAt(Storage.getStorageInstance().getValue(runNumber-1,8).length() - 1) == ']') {
            cardArt = Storage.fromStorageKey(Storage.getStorageInstance().getValue(runNumber-1,8).substring(0,Storage.getStorageInstance().getValue(runNumber-1,8).length()-1)).getArtLines();
            cardDesc = Storage.fromStorageKey(Storage.getStorageInstance().getValue(runNumber-1,8).substring(0,Storage.getStorageInstance().getValue(runNumber-1,8).length()-1)).getDeckName();
        }else {
            cardArt = Storage.fromStorageKey(Storage.getStorageInstance().getValue(runNumber-1,8)).getArtLines();
            cardDesc = Storage.fromStorageKey(Storage.getStorageInstance().getValue(runNumber-1,8)).getDeckName();
        }

        int screenSize = 11;
        String deckLabel = "\u001B[1;4mDECK\u001B[0m"; // Bold + Underlined
        String visibleCardLine = stripAnsi(cardArt[0]);
        int labelPadding = 78 + (visibleCardLine.length() - "DECK".length()) / 2;
        String paddedDeckLabel = String.format("%" + labelPadding + "s", deckLabel);
        optionLines.add(paddedDeckLabel);
        optionLines.add("");

        // Italicized deck name
        String italicDeckName = "\u001B[3m" + cardDesc + "\u001B[0m";
        visibleCardLine = stripAnsi(cardArt[0]);
        int deckNamePadding = 85 + (visibleCardLine.length() - "Standard Red Deck".length()) / 2;
        String paddedDeckName = String.format("%" + deckNamePadding + "s", italicDeckName);

        for (int i = 0; i < screenSize; i++) {
            String leftText = "";
            if (i == 1) leftText = "Round: " + Storage.getStorageInstance().getValue(runNumber-1,1);
            else if (i == 2) leftText = "Best Hand: " + Storage.getStorageInstance().getValue(runNumber-1,2);
            else if (i == 3) leftText = "Ante: " + Storage.getStorageInstance().getValue(runNumber-1,3);
            else if (i == 4) leftText = "Chips: $" + Storage.getStorageInstance().getValue(runNumber-1,4);
            else if (i == 5) leftText = "Wins: " + Storage.getStorageInstance().getValue(runNumber-1,5) +  " | Losses: " + Storage.getStorageInstance().getValue(runNumber-1,6);
            else if (i == 6) leftText = "------------------------";
            else if (i == 7) leftText = "Last Action: " + Storage.getStorageInstance().getValue(runNumber-1,7);

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
