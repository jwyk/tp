package javatro.display.screens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javatro.core.JavatroException;
import javatro.manager.options.ViewRunOption;
import javatro.storage.Storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class RunListScreenTest extends ScreenTest {

    private Storage storage;

    @BeforeEach
    public void setUp() {
        super.setUp();
        storage = Storage.getStorageInstance();
        Storage.saveActive = false;

        // Prepare mock data for the test
        TreeMap<Integer, ArrayList<String>> serializedRunData = new TreeMap<>();

        String rowData1 =
                "0,1,0,4,5,1,SMALL"
                    + " BLIND,0,0,RED,9D,2D,3D,6D,AC,5H,3C,KD,-,-,-,-,-,1,1,1,1,1,1,1,1,1,1,1,1,1,9S,2H,6H,3H,10D,4S,2S,10H,5S,KS,JH,4C,9C,3S,10C,QH,10S,5C,KC,AH,AD,2C,5D,QD,JD,4H,7S,8C,KH,AS,QC,8D,JC,QS,8H,8S,9H,6S,7C,6C,4D,7H,7D,JS,0be01025238ce77a8671d79d1884f7ed0223806cd9f9004300678b4d6b1fe565";
        String rowData2 =
                "1,1,0,4,5,1,LARGE"
                    + " BLIND,0,0,BLUE,9D,2D,3D,6D,AC,5H,3C,KD,-,-,-,-,-,1,1,1,1,1,1,1,1,1,1,1,1,1,9S,2H,6H,3H,10D,4S,2S,10H,5S,KS,JH,4C,9C,3S,10C,QH,10S,5C,KC,AH,AD,2C,5D,QD,JD,4H,7S,8C,KH,AS,QC,8D,JC,QS,8H,8S,9H,6S,7C,6C,4D,7H,7D,JS,1d82aa58d1a4d3f5e4afae4c8c1eb4bb5671ff5bfc9480c66b9484f7ae217e13";

        ArrayList<String> dataList1 = new ArrayList<>(Arrays.asList(rowData1.split(",")));
        ArrayList<String> dataList2 = new ArrayList<>(Arrays.asList(rowData2.split(",")));

        serializedRunData.put(0, dataList1);
        serializedRunData.put(1, dataList2);

        storage.setSerializedRunData(serializedRunData);
    }

    @Test
    public void testRunListScreenInitialization() throws JavatroException {
        RunListScreen runListScreen = new RunListScreen();

        assertEquals(
                2,
                runListScreen.getCommandMap().size(),
                "RunListScreen should have exactly 2 saved runs as options.");
    }

    @Test
    public void testRunListScreenOptionTypes() throws JavatroException {
        RunListScreen runListScreen = new RunListScreen();

        assertTrue(
                runListScreen.getCommandMap().get(0) instanceof ViewRunOption,
                "The first option should be a ViewRunOption.");
        assertTrue(
                runListScreen.getCommandMap().get(1) instanceof ViewRunOption,
                "The second option should be a ViewRunOption.");
    }

    @Test
    public void testRunListScreenRunNumbers() throws JavatroException {
        RunListScreen runListScreen = new RunListScreen();

        ViewRunOption firstOption = (ViewRunOption) runListScreen.getCommandMap().get(0);
        ViewRunOption secondOption = (ViewRunOption) runListScreen.getCommandMap().get(1);

        assertEquals(
                1, firstOption.getRunNumber(), "The first ViewRunOption should have run number 1.");
        assertEquals(
                2,
                secondOption.getRunNumber(),
                "The second ViewRunOption should have run number 2.");
    }

    @Test
    public void testRunListScreenNoRuns() throws JavatroException {
        storage.setSerializedRunData(new TreeMap<>()); // Reset the saved data
        RunListScreen runListScreen = new RunListScreen();

        assertEquals(
                0,
                runListScreen.getCommandMap().size(),
                "RunListScreen should have no options when there are no saved runs.");
    }
}
