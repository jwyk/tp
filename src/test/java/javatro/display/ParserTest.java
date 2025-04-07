package javatro.display;

import javatro.core.JavatroException;
import javatro.manager.JavatroManager;
import javatro.display.screens.Screen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

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
