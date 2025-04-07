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
  private final String SAVEFILE_PATH = "./savefile.csv";
  private static final String SAMPLE_DATA = "0,1,0,4,5,1,SMALL BLIND,0,0,RED,3H,5H,8H,10H,4H,JD,JH,10D,-,-,-,-,-,1,1,1,1,1,1,1,1,1,1,1,1,1,9D,10C,AC,6H,AS,2C,3C,AD,6C,JC,5D,6S,3S,7C,5C,QH,QS,10S,KD,7H,QC,7D,8S,QD,KS,2D,3D,9H,2H,JS,2S,4C,8C,9C,AH,4D,4S,KH,9S,6D,8D,7S,5S,KC,1cdb399415e1c91d8ef8b7bd443598dd014c2dc996e65e8b6c93f703f4001437";

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
    compareOutputToFile("RunSelectScreen_OneSave.txt");
  }

  @Override
  protected Class<?> getExpectedScreenType() {
    return RunSelectScreen.class;
  }


}


