package javatro.display.screens;

import static org.junit.jupiter.api.Assertions.*;

import javatro.core.JavatroException;
import javatro.manager.options.*;
import javatro.storage.Storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class RunSelectScreenTest extends ScreenTest {

    private RunSelectScreen runSelectScreen;
    private TreeMap<Integer, ArrayList<String>> serializedRunData;

    @BeforeEach
    public void setUp() {
        Storage.saveActive = false;

        Storage storage = Storage.getStorageInstance();
        serializedRunData = new TreeMap<>();

        // Load the test data into the TreeMap
        String rowData =
                "0,1,0,4,5,1,SMALL"
                    + " BLIND,0,0,RED,9D,2D,3D,6D,AC,5H,3C,KD,-,-,-,-,-,1,1,1,1,1,1,1,1,1,1,1,1,1,9S,2H,6H,3H,10D,4S,2S,10H,5S,KS,JH,4C,9C,3S,10C,QH,10S,5C,KC,AH,AD,2C,5D,QD,JD,4H,7S,8C,KH,AS,QC,8D,JC,QS,8H,8S,9H,6S,7C,6C,4D,7H,7D,JS,0be01025238ce77a8671d79d1884f7ed0223806cd9f9004300678b4d6b1fe565";

        // Split the row data by comma and store it as an ArrayList
        ArrayList<String> dataList = new ArrayList<>(Arrays.asList(rowData.split(",")));

        // Put the data list into the TreeMap with key 0
        serializedRunData.put(0, dataList);

        storage.setSerializedRunData(serializedRunData);

        Storage.getStorageInstance().setRunChosen(1);

        try {
            runSelectScreen = new RunSelectScreen();
        } catch (JavatroException e) {
            fail("Failed to initialize RunSelectScreen: " + e.getMessage());
        }

        super.setUp();
    }

    @Test
    public void testSerializedRunDataLoading() {
        assertNotNull(serializedRunData, "The serializedRunData map should not be null.");
        assertEquals(
                1,
                serializedRunData.size(),
                "The serializedRunData map should contain exactly one entry.");
        assertEquals(
                81, serializedRunData.get(0).size(), "The entry should contain exactly 81 items.");

        // Checking specific data points to ensure the data was loaded correctly
        assertEquals("0", serializedRunData.get(0).get(0));
        assertEquals("RED", serializedRunData.get(0).get(9));
        assertEquals("SMALL BLIND", serializedRunData.get(0).get(6));
        assertEquals("9D", serializedRunData.get(0).get(10));
        assertEquals(
                "0be01025238ce77a8671d79d1884f7ed0223806cd9f9004300678b4d6b1fe565",
                serializedRunData.get(0).get(80));
    }

    @Test
    public void testInitializationWithoutSaveData() {
        Storage.getStorageInstance().setSerializedRunData(new java.util.TreeMap<>());

        try {
            runSelectScreen = new RunSelectScreen();
        } catch (JavatroException e) {
            fail("Failed to initialize RunSelectScreen: " + e.getMessage());
        }

        assertNotNull(runSelectScreen, "RunSelectScreen should be initialized successfully.");
        assertEquals(
                1,
                runSelectScreen.getCommandMap().size(),
                "Only StartGameOption should be available.");
    }

    @Test
    public void testInitializationWithSaveData() {
        assertNotNull(runSelectScreen, "RunSelectScreen should be initialized successfully.");
        assertTrue(
                runSelectScreen.getCommandMap().size() > 1,
                "Commands should be available if save data exists.");
    }

    @Test
    public void testCommandMapContents() {
        List<Class<?>> expectedOptions =
                List.of(
                        StartRunOption.class,
                        ViewNextRunOption.class,
                        ViewPrevRunOption.class,
                        ViewRunListOption.class,
                        StartGameOption.class);

        List<Option> actualCommands = runSelectScreen.getCommandMap();
        List<Class<?>> actualCommandClasses = new ArrayList<>();

        for (Option option : actualCommands) {
            actualCommandClasses.add(option.getClass());
        }

        assertEquals(
                expectedOptions.size(),
                actualCommandClasses.size(),
                "Command map should contain the expected options.");

        for (Class<?> expectedOption : expectedOptions) {
            assertTrue(
                    actualCommandClasses.contains(expectedOption),
                    "Command map should contain: " + expectedOption.getSimpleName());
        }
    }

    @Test
    public void testDisplayCurrentChosenRun() {
        assertDoesNotThrow(
                RunSelectScreen::getRunNumber,
                "Displaying the current chosen run should not throw an exception.");
    }

    @Test
    public void testRunNumberGetterSetter() {
        runSelectScreen.setRunNumber(5);
        assertEquals(
                5,
                runSelectScreen.getRunNumber(),
                "Run number should be set and retrieved correctly.");
    }

    @Test
    public void testInvalidRunNumberHandling() {
        runSelectScreen.setRunNumber(-1);
        assertEquals(
                -1,
                runSelectScreen.getRunNumber(),
                "Negative run numbers should be handled appropriately.");
    }
}
