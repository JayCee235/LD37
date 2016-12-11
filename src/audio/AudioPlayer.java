package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	AudioInputStream in;
	AudioFormat format;
	DataLine.Info info;
	Clip clip;
	
	public AudioPlayer(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File f = new File(path);
		in = AudioSystem.getAudioInputStream(f);
		info = new DataLine.Info(Clip.class, format);
		clip = (Clip) AudioSystem.getLine(info);
	}
	
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	public void play() throws LineUnavailableException, IOException {
		clip.open(in);
		clip.start();
	}
	
	public void stop() {
		clip.stop();
		clip.close();
	}
	
	public void playLoop() throws LineUnavailableException, IOException {
		clip.open(in);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
