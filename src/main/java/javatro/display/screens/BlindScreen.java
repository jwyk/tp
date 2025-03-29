package javatro.display.screens;

import javatro.Javatro;
import javatro.core.Ante;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.manager.JavatroManager;
import javatro.manager.options.AcceptBlindOption;
import javatro.manager.options.HelpIntroOption;
import javatro.manager.options.RejectBlindOption;

import java.util.ArrayList;
import java.util.List;

public class BlindScreen extends Screen {

    public BlindScreen() throws JavatroException {
        super("BLIND MENU");

        commandMap.add(new AcceptBlindOption());
        commandMap.add(new RejectBlindOption());
    }

    @Override
    public void displayScreen() {
        int activeSelection = getCurrentBlindIndex();
        int sectionWidth = UI.BORDER_WIDTH / 3;

        drawTopBorder(activeSelection, sectionWidth);
        drawContentRows(activeSelection, sectionWidth);
        drawBottomBorder(activeSelection, sectionWidth);
    }

    private int getCurrentBlindIndex() {
        Ante.Blind currentBlind = JavatroCore.getAnte().getBlind();
        return (currentBlind == Ante.Blind.SMALL_BLIND) ? 0 :
                (currentBlind == Ante.Blind.LARGE_BLIND) ? 1 : 2;
    }

    private void drawTopBorder(int activeSelection, int sectionWidth) {
        System.out.print(getHighlightedChar(activeSelection == 0, UI.TOP_LEFT));

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                System.out.print(getHighlightedChar(activeSelection <= 1, UI.T_DOWN));
            }

            String horizontalLine = String.valueOf(UI.HORIZONTAL).repeat(sectionWidth - 1);
            System.out.print(getHighlightedChar(activeSelection == i, horizontalLine));

            if (i == 1) {
                System.out.print(getHighlightedChar(activeSelection >= 1, UI.T_DOWN));
            }
        }

        System.out.println(getHighlightedChar(activeSelection == 2, UI.TOP_RIGHT));
    }


    private void drawContentRows(int activeSelection, int sectionWidth) {
        List<String[]> contentRows = generateContentData();

        for (String[] row : contentRows) {
            System.out.print(getHighlightedChar(activeSelection == 0, UI.VERTICAL));

            for (int i = 0; i < 3; i++) {
                if (i == 1) {
                    System.out.print(getHighlightedChar(activeSelection <= 1, UI.VERTICAL));
                }

                String centeredText = centerInSection(row[i], sectionWidth - 1);
                System.out.print(getHighlightedChar(activeSelection == i, centeredText));

                if (i == 1) {
                    System.out.print(getHighlightedChar(activeSelection >= 1, UI.VERTICAL));
                }
            }

            System.out.println(getHighlightedChar(activeSelection == 2, UI.VERTICAL));
        }
    }

    private List<String[]> generateContentData() {
        List<String[]> contentRows = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            contentRows.add(new String[]{"", "", ""});
        }

        contentRows.add(new String[]{
                Ante.Blind.SMALL_BLIND.getName(),
                Ante.Blind.LARGE_BLIND.getName(),
                Ante.Blind.BOSS_BLIND.getName()
        });

        contentRows.add(new String[]{"", "", ""});

        contentRows.add(new String[]{
                String.valueOf((int)(JavatroCore.getAnte().getAnteScore() * Ante.Blind.SMALL_BLIND.getMultiplier())),
                String.valueOf((int)(JavatroCore.getAnte().getAnteScore() * Ante.Blind.LARGE_BLIND.getMultiplier())),
                String.valueOf((int)(JavatroCore.getAnte().getAnteScore() * Ante.Blind.BOSS_BLIND.getMultiplier()))
        });

        for (int i = 0; i < 6; i++) {
            contentRows.add(new String[]{"", "", ""});
        }

        return contentRows;
    }

    private void drawBottomBorder(int activeSelection, int sectionWidth) {
        System.out.print(getHighlightedChar(activeSelection == 0, UI.BOTTOM_LEFT));

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                System.out.print(getHighlightedChar(activeSelection <= 1, UI.T_UP));
            }

            String horizontalLine = String.valueOf(UI.HORIZONTAL).repeat(sectionWidth - 1);
            System.out.print(getHighlightedChar(activeSelection == i, horizontalLine));

            if (i == 1) {
                System.out.print(getHighlightedChar(activeSelection >= 1, UI.T_UP));
            }
        }

        System.out.println(getHighlightedChar(activeSelection == 2, UI.BOTTOM_RIGHT));
    }

    private String centerInSection(String text, int width) {
        int textLength = text.length();
        int padding = Math.max(0, width - textLength);
        int leftPad = padding / 2;
        int rightPad = padding - leftPad;

        return " ".repeat(leftPad) + text + " ".repeat(rightPad);
    }

    private String getHighlightedChar(boolean isActive, char character) {
        return getHighlightedChar(isActive, String.valueOf(character));
    }

    private String getHighlightedChar(boolean isActive, String text) {
        if (isActive) {
            return UI.YELLOW + text + UI.END;
        } else {
            return text;
        }
    }
}