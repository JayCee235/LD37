package test;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import audio.AudioPlayer;
import gui.Window;

public class MusicTest {
	public static void main(String[] args) {
		Window w = new Window("Music test");
		AudioPlayer ap;
		try {
			ap = new AudioPlayer("./res/Final Harvest.wav");
			w.display();
			ap.playLoop();
			ap.stopLoop();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
}
