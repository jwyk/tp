package display.screens;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import javatro.core.JavatroCore;
import javatro.core.JavatroException;
import javatro.display.UI;
import javatro.display.screens.Screen;
import javatro.display.screens.StartScreen;
import javatro.manager.JavatroManager;
import javatro.manager.options.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

// ParentScreenTest.java

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

// ParentScreenTest.java

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public abstract class ScreenTest {

  protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  protected final PrintStream originalOut = System.out;

  private static final Pattern ANSI_PATTERN = Pattern.compile("\u001B\\[[;\\d]*m");

  protected JavatroManager javatroManager;

  @BeforeEach
  public void setUp() {
    UI javatroView = new UI();

    JavatroCore javatroCore = new JavatroCore();

    try {
      javatroManager = new JavatroManager(javatroView, javatroCore);
    } catch (JavatroException e) {
      throw new RuntimeException(e);
    }

    JavatroManager.runningTests = true;
    System.out.println("Setting up test for: " + this.getClass().getSimpleName());

    System.setOut(new PrintStream(outContent));
  }

  protected void compareCommandListTypes(List<Class<?>> expectedTypes, List<Option> actualList) {
    if (expectedTypes.size() != actualList.size()) {
      fail("Mismatch in number of commands. Expected: " + expectedTypes.size() + ", but got: " + actualList.size());
    }

    for (int i = 0; i < expectedTypes.size(); i++) {
      Class<?> expectedType = expectedTypes.get(i);
      Option actualOption = actualList.get(i);

      if (!expectedType.isInstance(actualOption)) {
        fail("Mismatch at index " + i + ". Expected type: " + expectedType.getSimpleName() +
            ", but got: " + actualOption.getClass().getSimpleName());
      }
    }
  }

  protected void compareScreenType(Object screen, Class<?> expectedType) {
    if (!expectedType.isInstance(screen)) {
      fail("Expected screen type: " + expectedType.getSimpleName() +
          ", but got: " + screen.getClass().getSimpleName());
    }
  }

  @Test
  public void testStartScreenType() {
    // Get the current screen
    Screen currentScreen = UI.getCurrentScreen();

    // Use the inherited compareScreenType method to check type
    compareScreenType(currentScreen, StartScreen.class);
  }

  // Abstract method to be implemented by child classes
  protected abstract Class<?> getExpectedScreenType();

  @AfterEach
  public void tearDown() {
    System.setOut(originalOut);
    outContent.reset();
  }

  protected String getOutput() {
    return outContent.toString().trim();
  }

  protected String readExpectedOutput(String fileName) throws IOException {
    String path = "src/test/resources/screens/" + fileName;
    return Files.readString(Paths.get(path)).trim();
  }

  protected void compareOutputToFile(String fileName) throws IOException {
    String actualOutput = getOutput();
    String expectedOutput = readExpectedOutput(fileName);

    if (!actualOutput.equals(expectedOutput)) {
      fail("Output mismatch for file: " + fileName);
    }
  }






}
