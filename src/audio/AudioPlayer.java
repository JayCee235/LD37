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
	File f;
	AudioInputStream in;
	AudioFormat format;
	DataLine.Info info;
	Clip clip;
	
	public AudioPlayer(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		f = new File(path);
		in = AudioSystem.getAudioInputStream(f);
		info = new DataLine.Info(Clip.class, format);
		clip = (Clip) AudioSystem.getLine(info);
	}
	
	private void resetClip() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		in = AudioSystem.getAudioInputStream(f);
		info = new DataLine.Info(Clip.class, format);
		clip = (Clip) AudioSystem.getLine(info);
	}
	
	public boolean isPlaying() {
		return clip.isActive();
	}
	
	public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		resetClip();
		clip.open(in);
		clip.start();
	}
	
	public void stop() {
		clip.stop();
		clip.flush();
		clip.close();
	}
	
	public void playLoop() throws LineUnavailableException, IOException {
		if(clip.isOpen()) clip.close();
		clip.open(in);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopLoop() {
		clip.loop(0);
	}
}
