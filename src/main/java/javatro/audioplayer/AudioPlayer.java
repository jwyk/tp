package javatro.audioplayer;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioPlayer implements Runnable {

    private final String audioPath;

    public AudioPlayer(String audioPath) {
        this.audioPath = audioPath;
    }

    @Override
    public void run() {
        try {
            File audioFile = new File(audioPath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Start playing the audio
            audioClip.start();

            // Allow the clip to complete playing before closing resources
            audioClip.drain();

            audioStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
