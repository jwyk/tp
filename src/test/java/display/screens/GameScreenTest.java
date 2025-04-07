package display.screens;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import javatro.audioplayer.AudioPlayer;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.GameScreen;
import javatro.display.screens.RunSelectScreen;
import javatro.display.screens.StartScreen;
import javatro.manager.JavatroManager;
import javatro.manager.options.DeckViewOption;
import javatro.manager.options.DiscardCardOption;
import javatro.manager.options.ExitGameOption;
import javatro.manager.options.LoadJumpToRunScreenOption;
import javatro.manager.options.MainMenuOption;
import javatro.manager.options.Option;
import javatro.manager.options.PlayCardOption;
import javatro.manager.options.PokerHandOption;
import javatro.manager.options.SeeNextRunOption;
import javatro.manager.options.SeePreviousRun;
import javatro.manager.options.StartGameOption;
import javatro.manager.options.StartRunNumberOption;
import javatro.storage.Storage;
import javatro.utilities.csvutils.CSVUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameScreenTest extends ScreenTest {

  @BeforeEach
  public void setUp() {
    try {
      CSVUtils.writeSampleToCSV(SAVEFILE_PATH, SAMPLE_DATA);
    } catch (IOException e) {
      fail("Failed To Write To CSV: " + e);
    }

    Storage storage = Storage.getStorageInstance();
    super.setUp();

    try {
      storage.setRunChosen(1);
      JavatroManager.beginGame(
          (Storage.DeckFromKey(
              storage.getValue(storage.getRunChosen() - 1, Storage.DECK_INDEX))));

      JavatroManager.jc.beginGame();
      JavatroCore.currentRound.addPropertyChangeListener(javatro.display.UI.getGameScreen());
      JavatroCore.currentRound.updateRoundVariables();
      System.out.println(UI.getGameScreen().getCommandMap().size());
    } catch (JavatroException e) {
      fail("Failed to load the game screen: " + e);
    }
  }

  @Test
  public void testCommandMatchWithSave() {
    try {
      JavatroManager.ui.setCurrentScreen(UI.getGameScreen());
    } catch (JavatroException e) {
      throw new RuntimeException(e);
    }
    List<Class<?>> expectedCommands = List.of(
        PlayCardOption.class,
        DiscardCardOption.class,
        PokerHandOption.class,
        DeckViewOption.class,
        MainMenuOption.class,
        ExitGameOption.class
    );

    List<Option> actualCommands = UI.getCurrentScreen().getCommandMap();
    compareCommandListTypes(expectedCommands, actualCommands);
  }

  @Test
  public void testRunSelectScreenOutputWithSave() throws IOException {
    pipeOutputToFile("data.txt", UI.getGameScreen());
    compareOutputToFile2("GameScreen.txt");
  }



}


