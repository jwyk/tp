package display.screens;

// StartScreenTest.java
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import javatro.audioplayer.AudioPlayer;
import javatro.core.JavatroException;
import javatro.display.UI;
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
    expectedCommands.add(LoadRunSelectOption.class);
    expectedCommands.add(HelpMenuOption.class);
    expectedCommands.add(ExitGameOption.class);

    List<Option> actualCommands = UI.getCurrentScreen().getCommandMap();

    compareCommandListTypes(expectedCommands,actualCommands);
  }

  @Test
  public void testStartScreenOutput() throws IOException {
    // Compare the captured output with the file content
    compareOutputToFile("StartScreen.txt");
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

