package javatro.audioplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;

public class AudioPlayer implements Runnable {

    private final String audioPath;

    public AudioPlayer(String audioPath) {
        this.audioPath = audioPath;
    }

    @Override
    public void run() {
        try {
            // Load the audio file from resources
            InputStream inputStream = getClass().getResourceAsStream("/" + audioPath);

            if (inputStream == null) {
                throw new IOException("Audio file not found: " + audioPath);
            }

            // Wrap the input stream with BufferedInputStream
            BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedStream);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Make the audio loop continuously
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start playing the audio
            audioClip.start();

            // Keep the thread alive while audio is playing
            while (audioClip.isRunning()) {
                Thread.sleep(1000); // Sleep to avoid busy-waiting
            }

            audioStream.close();
        } catch (UnsupportedAudioFileException
                | IOException
                | LineUnavailableException
                | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
