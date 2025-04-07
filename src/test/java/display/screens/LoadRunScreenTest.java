package display.screens;



import javatro.storage.Storage;

import org.junit.jupiter.api.BeforeEach;

public class LoadRunScreenTest extends ScreenTest {

    @BeforeEach
    public void setUp() {

        Storage.getStorageInstance().resetStorage();

        super.setUp();
    }
}
