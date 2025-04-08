package javatro.display;

import static javatro.display.UI.BLUE;
import static javatro.display.UI.BOLD;
import static javatro.display.UI.ORANGE;
import static javatro.display.UI.PURPLE;
import static javatro.display.UI.RED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javatro.core.Card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardRendererTest {

    private Card heartsCard;
    private Card diamondsCard;
    private Card clubsCard;
    private Card spadesCard;

    @BeforeEach
    public void setUp() {
        heartsCard = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        diamondsCard = new Card(Card.Rank.KING, Card.Suit.DIAMONDS);
        clubsCard = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        spadesCard = new Card(Card.Rank.JACK, Card.Suit.SPADES);
    }

    @Test
    public void testRenderCardHearts() {
        String[] cardArt = CardRenderer.renderCard(heartsCard);

        assertNotNull(cardArt, "Card art should not be null");
        assertEquals(5, cardArt.length, "Card art should have 5 lines");
        assertTrue(cardArt[0].contains("A"), "Top line should contain the card rank symbol");
        assertTrue(cardArt[2].contains("H"), "Middle line should contain the suit symbol");
        assertTrue(cardArt[4].contains("A"), "Bottom line should contain the card rank symbol");
    }

    @Test
    public void testRenderCardDiamonds() {
        String[] cardArt = CardRenderer.renderCard(diamondsCard);

        assertNotNull(cardArt, "Card art should not be null");
        assertEquals(5, cardArt.length, "Card art should have 5 lines");
        assertTrue(cardArt[0].contains("K"), "Top line should contain the card rank symbol");
        assertTrue(cardArt[2].contains("D"), "Middle line should contain the suit symbol");
        assertTrue(cardArt[4].contains("K"), "Bottom line should contain the card rank symbol");
    }

    @Test
    public void testRenderCardClubs() {
        String[] cardArt = CardRenderer.renderCard(clubsCard);

        assertNotNull(cardArt, "Card art should not be null");
        assertEquals(5, cardArt.length, "Card art should have 5 lines");
        assertTrue(cardArt[0].contains("Q"), "Top line should contain the card rank symbol");
        assertTrue(cardArt[2].contains("C"), "Middle line should contain the suit symbol");
        assertTrue(cardArt[4].contains("Q"), "Bottom line should contain the card rank symbol");
    }

    @Test
    public void testRenderCardSpades() {
        String[] cardArt = CardRenderer.renderCard(spadesCard);

        assertNotNull(cardArt, "Card art should not be null");
        assertEquals(5, cardArt.length, "Card art should have 5 lines");
        assertTrue(cardArt[0].contains("J"), "Top line should contain the card rank symbol");
        assertTrue(cardArt[2].contains("S"), "Middle line should contain the suit symbol");
        assertTrue(cardArt[4].contains("J"), "Bottom line should contain the card rank symbol");
    }

    @Test
    public void testRenderCardNullCard() {
        assertThrows(
                AssertionError.class,
                () -> CardRenderer.renderCard(null),
                "Should throw AssertionError when rendering a null card.");
    }

    @Test
    public void testGetSuitSymbol() {
        String[] heartsArt = CardRenderer.renderCard(heartsCard);
        String[] diamondsArt = CardRenderer.renderCard(diamondsCard);
        String[] clubsArt = CardRenderer.renderCard(clubsCard);
        String[] spadesArt = CardRenderer.renderCard(spadesCard);

        assertTrue(heartsArt[2].contains("H"), "Hearts card should contain 'H'.");
        assertTrue(diamondsArt[2].contains("D"), "Diamonds card should contain 'D'.");
        assertTrue(clubsArt[2].contains("C"), "Clubs card should contain 'C'.");
        assertTrue(spadesArt[2].contains("S"), "Spades card should contain 'S'.");
    }

    @Test
    public void testGetColourHearts() {
        String[] cardArt = CardRenderer.renderCard(heartsCard);
        assertTrue(
                cardArt[0].contains(BOLD) && cardArt[0].contains(RED),
                "Hearts card should use RED color.");
    }

    @Test
    public void testGetColourDiamonds() {
        String[] cardArt = CardRenderer.renderCard(diamondsCard);
        assertTrue(
                cardArt[0].contains(BOLD) && cardArt[0].contains(ORANGE),
                "Diamonds card should use ORANGE color.");
    }

    @Test
    public void testGetColourClubs() {
        String[] cardArt = CardRenderer.renderCard(clubsCard);
        assertTrue(
                cardArt[0].contains(BOLD) && cardArt[0].contains(BLUE),
                "Clubs card should use BLUE color.");
    }

    @Test
    public void testGetColourSpades() {
        String[] cardArt = CardRenderer.renderCard(spadesCard);
        assertTrue(
                cardArt[0].contains(BOLD) && cardArt[0].contains(PURPLE),
                "Spades card should use PURPLE color.");
    }
}
