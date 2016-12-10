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

public class Tile implements Drawable{
	public static final int WIDTH = 8, HEIGHT = 8;
	public static final int GRASS = 0;
	public static final int DIRT = 1;
	public static final Image[] SPRITES = new Image[2];
	
	int type;
	
	public Tile(int type) {
		this.type = type;
	}
	
	public static void load() {
		String[] paths = new String[SPRITES.length];
		
		String b = "./res/";
		String a = ".png";
		
		paths[0] = b+"testGrass"+a;
		paths[1] = b+"testFarm"+a;
		
		try {
			for(int i = 0; i < SPRITES.length; i++) {
				SPRITES[i] = ImageIO.read(new File(paths[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g) {
		Image sprite = SPRITES[this.type];
		g.drawImage(sprite, 0, 0, WIDTH*Drawable.SCALE, HEIGHT*Drawable.SCALE, 
				0, 0, WIDTH, HEIGHT, null);
	}
	
}
