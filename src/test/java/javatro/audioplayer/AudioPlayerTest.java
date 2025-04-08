package javatro.audioplayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AudioPlayerTest {

    private static final String VALID_AUDIO_PATH = "audioplayer/balatro_theme.wav";
    private static final String INVALID_AUDIO_PATH = "audioplayer/invalid_audio.wav";
    private AudioPlayer audioPlayer;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        audioPlayer = AudioPlayer.getInstance();
        AudioPlayer.getInstance().stopAudio(); // Ensure audio is not playing before each test

        // Redirect System.err to capture error messages
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        AudioPlayer.getInstance().stopAudio(); // Ensure audio is stopped after each test
        System.setErr(originalErr); // Reset System.err
    }

    @Test
    public void testStopAudio() {
        Assertions.assertDoesNotThrow(
                () -> AudioPlayer.getInstance().stopAudio(),
                "Audio stopping should not cause any exceptions.");
    }

}
