package display.screens;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import javatro.display.UI;
import javatro.manager.options.Option;
import javatro.manager.options.StartGameOption;
import javatro.storage.Storage;
import javatro.utilities.csvutils.CSVUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoadRunScreenTest extends ScreenTest {

  @BeforeEach
  public void setUp() {

    Storage.getStorageInstance().resetStorage();

    super.setUp();

  }

  @Test
  public void testCommandMatchWithSave() {
    List<Class<?>> expectedCommands = List.of(
        StartGameOption.class
    );

    List<Option> actualCommands = UI.getRunSelectScreen().getCommandMap();
    compareCommandListTypes(expectedCommands, actualCommands);
  }

  @Test
  public void testRunSelectScreenOutputWithSave() throws IOException {
    try {
      CSVUtils.clearCSVFile(SAVEFILE_PATH);
    } catch (IOException e) {
      System.out.println("Could not update savefile");
    }

    Storage.getStorageInstance().setRunChosen(1);

    pipeOutputToFile("data.txt", UI.getRunSelectScreen());
    compareOutputToFile2("RunSelectScreen_EmptySave.txt");
  }


}


