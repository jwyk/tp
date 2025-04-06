package javatro.audioplayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer implements Runnable {

    private final String filePath;
    private Clip audioClip;

    public AudioPlayer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();

            // Convert to PCM if necessary
            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        format.getSampleRate(),
                        16,
                        format.getChannels(),
                        format.getChannels() * 2,
                        format.getSampleRate(),
                        false
                );

                audioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
                format = decodedFormat;
            }

            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously
            audioClip.start();

            System.out.println("Audio is playing...");

            // Keep the thread alive while audio is playing
            while (audioClip.isRunning()) {
                Thread.sleep(100);
            }

            audioClip.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
        }
    }
}
