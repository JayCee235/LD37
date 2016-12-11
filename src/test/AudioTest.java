package test;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import audio.AudioPlayer;
import gui.Window;

public class AudioTest {
	public static void main(String[] args) {
		try {
			Window w = new Window("Audio Test");
			AudioPlayer ap = new AudioPlayer("./res/placementSound.wav");
			w.display();
			ap.playLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
