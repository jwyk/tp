package javatro.display;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserTest {

    private Parser parser;
    private Screen mockScreen;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
        JavatroManager.runningTests = true; // Ensure runningTests is set to true
    }

    @Test
    public void testAddNullPropertyChangeListener() {
        assertThrows(AssertionError.class, () -> parser.addPropertyChangeListener(null));
    }
}
