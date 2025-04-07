package display.screens;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.RunSelectScreen;
import javatro.manager.options.LoadJumpToRunScreenOption;
import javatro.manager.options.Option;
import javatro.manager.options.SeeNextRunOption;
import javatro.manager.options.SeePreviousRun;
import javatro.manager.options.StartGameOption;
import javatro.manager.options.StartRunNumberOption;
import javatro.utilities.csvutils.CSVUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadRunScreenWithSaveFileTest extends ScreenTest {

  @BeforeEach
  public void setUp() {
    try {
      CSVUtils.writeSampleToCSV(SAVEFILE_PATH, SAMPLE_DATA);
    } catch (IOException e) {
      System.out.println("Could not update savefile");
    }

    super.setUp();

    try {
      UI.getStartScreen().getCommand(0).execute();
    } catch (JavatroException e) {
      fail("Failed to Set Screen: " + e.getMessage());
    }
  }

  @Test
  public void testCommandMatchWithSave() {
    List<Class<?>> expectedCommands = List.of(
        StartRunNumberOption.class,
        SeeNextRunOption.class,
        SeePreviousRun.class,
        LoadJumpToRunScreenOption.class,
        StartGameOption.class
    );

    List<Option> actualCommands = UI.getCurrentScreen().getCommandMap();
    compareCommandListTypes(expectedCommands, actualCommands);
  }

  @Test
  public void testRunSelectScreenOutputWithSave() throws IOException {
    pipeOutputToFile("data.txt", UI.getRunSelectScreen());

    compareOutputToFile2("RunSelectScreen_OneSave.txt");
  }


}


