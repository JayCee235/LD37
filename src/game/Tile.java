package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import gui.Drawable;

public abstract class Tile implements Drawable{
	public static final int WIDTH = 8, HEIGHT = 8;
	protected Image sprite;
	
	public Tile(String path) {
		Path p = Paths.get(path);
		sprite = null;
		try {
			sprite = ImageIO.read(p.toFile());
		} catch (IOException e) {
			System.err.println("Couldn't load image! " + path);
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(sprite, 0, 0, WIDTH*4, HEIGHT*4, 
				0, 0, WIDTH, HEIGHT, null);
	}
	
}
