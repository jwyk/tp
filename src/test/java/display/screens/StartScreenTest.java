package display.screens;

// StartScreenTest.java
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javatro.audioplayer.AudioPlayer;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.manager.JavatroManager;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.HelpMenuOption;
import javatro.manager.options.LoadRunSelectOption;
import javatro.manager.options.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javatro.display.screens.StartScreen;

public class StartScreenTest extends ScreenTest {

  @BeforeEach
  public void setUp() {
    super.setUp();
    try {
      JavatroManager.setScreen(new StartScreen());
    } catch (JavatroException e) {
      System.out.println("Failed to Set Screen: " + e.getMessage());
    }
  }

  @Override
  protected Class<?> getExpectedScreenType() {
    return StartScreen.class;
  }

  @Test
  public void commandMatchCheck() {
    List<Class<?>> expectedCommands = new ArrayList<>();
    expectedCommands.add(LoadRunSelectOption.class);
    expectedCommands.add(HelpMenuOption.class);
    expectedCommands.add(ExitGameOption.class);

    List<Option> actualCommands = UI.getStartScreen().getCommandMap();

    compareCommandListTypes(expectedCommands,actualCommands);
  }

  @Test
  public void testStartScreenOutput() throws IOException, JavatroException {
    // Compare the captured output with the file content
    compareOutputToFile("StartScreen.txt");
  }

  @Test
  public void testAccessingClearedCommandMap() {
    Screen currentScreen = UI.getCurrentScreen();
    currentScreen.clearCommandMap();  // Clear all commands

    Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
      currentScreen.getCommandMap().get(0).execute();  // Attempting to access the first command in an empty list
    });

    assertTrue(exception.getMessage().contains("Index 0 out of bounds"));
  }

  // AudioPlayer Handling
  @Test
  public void testAudioHandling() {
    try {
      AudioPlayer.getInstance().stopAudio(); // Stop audio before playing
      assertDoesNotThrow(() -> AudioPlayer.getInstance().playAudio("audioplayer/balatro_theme.wav"),
          "Audio should play successfully if file exists.");
      AudioPlayer.getInstance().stopAudio(); // Test stopping audio
    } catch (Exception e) {
      fail("Audio handling caused an unexpected exception: " + e.getMessage());
    }
  }






}

