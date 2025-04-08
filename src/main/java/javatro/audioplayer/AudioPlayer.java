package javatro.audioplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

public class AudioPlayer {

    private static AudioPlayer instance;
    private Clip audioClip;
    private Thread audioThread;
    private String currentAudioPath;

    private AudioPlayer() {}

    public static synchronized AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    public synchronized void playAudio(String audioPath) {
        if (audioPath.equals(currentAudioPath) && isPlaying()) {
            return; // Avoid restarting the same audio
        }
        switchAudio(audioPath);
    }

    public synchronized void switchAudio(String newAudioPath) {
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
    }

    public synchronized boolean isPlaying() {
        return audioClip != null && audioClip.isRunning();
    }

    public synchronized String getCurrentAudioPath() {
        return currentAudioPath;
    }
}
