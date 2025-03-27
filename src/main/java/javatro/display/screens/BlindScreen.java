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

public class BlindScreen extends Screen{
    public BlindScreen() throws JavatroException{
        super("BLIND MENU");

        commandMap.add(new AcceptBlindOption());
        commandMap.add(new RejectBlindOption());
    }

    @Override
    public void displayScreen()  {
        BorderStyle activeStyle = BorderStyle.DOUBLE;
        BorderStyle inactiveStyle = BorderStyle.SINGLE;
        int c = (JavatroManager.ante.getBlind() == Ante.Blind.SMALL_BLIND) ? 0 : (JavatroManager.ante.getBlind() == Ante.Blind.LARGE_BLIND)?1:2;
        int h = 15;
        int w = 80;

        System.out.print(getColoredSymbol(c == 0, c == 0 ? activeStyle.topLeft : inactiveStyle.topLeft));

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                System.out.print(getColoredSymbol(c == 0 || c == 1, (c == 0 || c == 1) ? activeStyle.topIntersection : inactiveStyle.topIntersection));
            }
            for (int j = 0; j < w/3; j++) {
                System.out.print(getColoredSymbol(i == c, i == c ? activeStyle.horizontal : inactiveStyle.horizontal));
            }
            if (i == 1) {
                System.out.print(getColoredSymbol(c == 1 || c == 2, (c == 1 || c == 2) ? activeStyle.topIntersection : inactiveStyle.topIntersection));
            }
        }


        System.out.println(getColoredSymbol(c == 2, c == 2 ? activeStyle.topRight : inactiveStyle.topRight));
        for (int i = 1; i < h - 1; i++) {
            System.out.print(getColoredSymbol(c == 0, c == 0 ? activeStyle.vertical : inactiveStyle.vertical));

            for (int l = 0; l < 3; l++) {
                if (l == 1) {
                    System.out.print(getColoredSymbol(c == 0 || c == 1, (c == 0 || c == 1) ? activeStyle.vertical : inactiveStyle.vertical));
                }

                Ante.Blind blind = (l == 0) ? Ante.Blind.SMALL_BLIND : (l == 1) ? Ante.Blind.LARGE_BLIND : Ante.Blind.BOSS_BLIND;
                if (i == h / 2 - 1) {
                    System.out.print(getColoredSymbol(c==l, centerPad(blind.getName(), w / 3)));
                }
                else if (i == h / 2 + 1){
                    System.out.print(getColoredSymbol(c==l, centerPad(String.valueOf((int)(JavatroManager.ante.getAnteScore()*blind.getMultiplier())), w / 3)));
                }
                else System.out.print(centerPad(" ", w/3));


                if (l == 1) {
                    System.out.print(getColoredSymbol(c == 1 || c == 2, (c == 1 || c == 2) ? activeStyle.vertical : inactiveStyle.vertical));
                }
            }

            System.out.print(getColoredSymbol(c == 2, c == 2 ? activeStyle.vertical : inactiveStyle.vertical));
            System.out.println();
        }

        System.out.print(getColoredSymbol(c == 0, c == 0 ? activeStyle.bottomLeft : inactiveStyle.bottomLeft));

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                System.out.print(getColoredSymbol(c == 0 || c == 1, (c == 0 || c == 1) ? activeStyle.bottomIntersection : inactiveStyle.bottomIntersection));
            }
            for (int j = 0; j < w/3; j++) {
                System.out.print(getColoredSymbol(i == c, i == c ? activeStyle.horizontal : inactiveStyle.horizontal));
            }
            if (i == 1) {
                System.out.print(getColoredSymbol(c == 1 || c == 2, (c == 1 || c == 2) ? activeStyle.bottomIntersection : inactiveStyle.bottomIntersection));
            }
        }

        System.out.println(getColoredSymbol(c == 2, c == 2 ? activeStyle.bottomRight : inactiveStyle.bottomRight));
    }

    private enum BorderStyle {
        SINGLE("┌", "┐", "└", "┘", "─", "│", "┬", "┴", "┼"),
        DOUBLE("╔", "╗", "╚", "╝", "═", "║", "╦", "╩", "╬");

        private final String topLeft;
        private final String topRight;
        private final String bottomLeft;
        private final String bottomRight;
        private final String horizontal;
        private final String vertical;
        private final String topIntersection;
        private final String bottomIntersection;
        private final String crossIntersection;

        BorderStyle(String topLeft, String topRight, String bottomLeft, String bottomRight,
                    String horizontal, String vertical, String topIntersection,
                    String bottomIntersection, String crossIntersection) {
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.topIntersection = topIntersection;
            this.bottomIntersection = bottomIntersection;
            this.crossIntersection = crossIntersection;
        }
    }

    private enum Color {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        MAGENTA("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");

        private final String code;

        Color(String code) {
            this.code = code;
        }
    }

    private static String centerPad(String str, int size) {
        if (str.length() >= size) {
            return str;
        }

        int totalPadding = size - str.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return " ".repeat(leftPadding) + str + " ".repeat(rightPadding);
    }

    private static String getColoredSymbol(boolean isActive, String symbol) {
        return (isActive ? Color.YELLOW.code: Color.RESET.code) + symbol + Color.RESET.code;
    }
}
