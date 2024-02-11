package main;
import javax.sound.sampled.*;
import java.net.URL;

public class sound {
    private Clip musicClip;
    private URL[] url = new URL[10];

    public sound() {
        url[0] = getClass().getResource("/res/Tetris.wav");
        url[1] = getClass().getResource("/res/delete line.wav");
        url[2] = getClass().getResource("/res/gameover.wav");
        url[3] = getClass().getResource("/res/rotation.wav");
        url[4] = getClass().getResource("/res/touch floor.wav");
    }

    public void play(int i, boolean music) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
                musicClip.open(ais);
            } else {
                clip.open(ais);
            }

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });

            clip.start();
        } catch (Exception e) {
            e.printStackTrace(); // Adicione isso para imprimir detalhes da exceção no console
        }
    }

    public void loop() {
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }
}
