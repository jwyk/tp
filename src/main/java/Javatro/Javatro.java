package Javatro;

import javatro_manager.JavatroManager;


import javatro_view.JavatroView;
import static javatro_core.Card.Rank.ACE;
import static javatro_core.Card.Rank.TEN;
import static javatro_core.Card.Rank.THREE;
import static javatro_core.Card.Suit.HEARTS;

import java.util.List;
import javatro_core.Card;
import javatro_core.HandResult;
import javatro_core.PokerHand;
import javatro_view.StartScreen;

import java.io.IOException;

/** The main class for the Javatro game. This class runs the game. */
// Primary view class that handles current view state etc
public class Javatro {
    private static final JavatroView javatroView = new JavatroView(); // View
    private static final JavatroManager javatroManager = new JavatroManager(javatroView);

    public static void main(String[] args) throws IOException {
        JavatroManager.setScreen(new StartScreen());
    }
}
