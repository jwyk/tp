// @@author flyingapricot
package javatro.audioplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The {@code AudioPlayer} class provides functionality for playing, stopping, and switching audio
 * files. This class is implemented as a singleton to ensure only one instance of the player is
 * active at any given time. It supports looping audio files continuously.
 */
public class AudioPlayer {

    private static AudioPlayer instance;
    private Clip audioClip;
    private Thread audioThread;
    private String currentAudioPath;

    /** Private constructor to prevent instantiation from other classes. */
    private AudioPlayer() {}

    /**
     * Returns the singleton instance of {@code AudioPlayer}.
     *
     * @return The singleton {@code AudioPlayer} instance.
     */
    public static synchronized AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    /**
     * Plays an audio file. If the specified audio file is already playing, this method returns
     * without restarting it.
     *
     * @param audioPath The path of the audio file to play. Must not be null or empty.
     */
    public synchronized void playAudio(String audioPath) {
        assert audioPath != null : "Audio path cannot be null";
        assert !audioPath.trim().isEmpty() : "Audio path cannot be empty";

        if (audioPath.equals(currentAudioPath) && isPlaying()) {
            return; // Avoid restarting the same audio
        }
        switchAudio(audioPath);
    }

    /**
     * Switches to a new audio file, stopping any currently playing audio.
     *
     * @param newAudioPath The path of the new audio file to play. Must not be null or empty.
     */
    public synchronized void switchAudio(String newAudioPath) {
        assert newAudioPath != null : "New audio path cannot be null";
        assert !newAudioPath.trim().isEmpty() : "New audio path cannot be empty";

        stopAudio(); // Stop the current audio first

        audioThread =
                new Thread(
                        () -> {
                            try {
                                currentAudioPath = newAudioPath;

                                // Load the audio file from resources
                                InputStream inputStream =
                                        getClass().getResourceAsStream("/" + newAudioPath);
                                if (inputStream == null) {
                                    throw new IOException("Audio file not found: " + newAudioPath);
                                }

                                BufferedInputStream bufferedStream =
                                        new BufferedInputStream(inputStream);
                                AudioInputStream audioStream =
                                        AudioSystem.getAudioInputStream(bufferedStream);

                                audioClip = AudioSystem.getClip();
                                audioClip.open(audioStream);
                                audioClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop indefinitely
                                audioClip.start();

                                // Assert that the audio clip has successfully started
                                assert audioClip.isRunning() : "Audio clip failed to start";

                                // Keep the thread alive while the audio is playing
                                while (audioClip != null && audioClip.isRunning()) {
                                    try {
                                        Thread.sleep(
                                                100); // Check every 100ms if the audio is still
                                                      // playing
                                    } catch (InterruptedException e) {
                                        break; // Exit if interrupted
                                    }
                                }

                                audioStream.close();

                            } catch (UnsupportedAudioFileException
                                    | IOException
                                    | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                        });

        audioThread.setDaemon(true); // Ensure the thread doesn't block the application from exiting
        audioThread.start();
    }

    /** Stops the currently playing audio, if any, and properly cleans up resources. */
    public synchronized void stopAudio() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
            audioClip = null;
            currentAudioPath = null;
        }

        if (audioThread != null && audioThread.isAlive()) {
            try {
                audioThread.join(); // Wait for the audio thread to finish properly
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Assert that no audio is playing
        assert audioClip == null : "Audio clip should be null after stopping audio";
    }

    /**
     * Checks if an audio file is currently playing.
     *
     * @return {@code true} if an audio file is playing, {@code false} otherwise.
     */
    public synchronized boolean isPlaying() {
        return audioClip != null && audioClip.isRunning();
    }

    /**
     * Returns the path of the currently playing audio file.
     *
     * @return The path of the currently playing audio file, or {@code null} if no audio is playing.
     */
    public synchronized String getCurrentAudioPath() {
        return currentAudioPath;
    }
}
