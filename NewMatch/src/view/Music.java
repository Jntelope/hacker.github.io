package view;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
public class Music extends JFrame {
    public static synchronized void Click() {
        Runnable cli = new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
//                    System.out.println("Play BGM");
//                    System.out.println(new File("../").getAbsolutePath());
                    clip.open(AudioSystem.getAudioInputStream(new File("res/Drip.wav")));
                    clip.start();
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(cli).start();
    }
}