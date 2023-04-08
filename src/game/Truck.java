package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Truck extends Sprite{
	// amount to move x and y direction
	private int dx;
	private int dy;
	
	// dimensions of screen to ensure we aren't falling off the road
	private int maxX = 1000;
	private int maxY = 1200;
	
	// current x / y position
//	private int x = 40;
//	private int y = 60;
	
	// image height and width
	private int w;
	private int h;
	
	// image object
	private Image image;
	
	// image uri
	private String imgPath = "/truck.png";
	
	// missiles
	private List<Missle> miss;

	private int missles = 3;
	
	public Truck(int x, int y){
		super(x, y);
		
		initTruck();
	}
	
	private void initTruck() {

		miss = new ArrayList<>();
		loadImage();
	}
	
	private void loadImage() {
		java.net.URL url = Road.class.getResource(imgPath);
		ImageIcon imgI = new ImageIcon(url);
		image = imgI.getImage();
		
		w = image.getWidth(null);
		h = image.getHeight(null);
	}
	
	public void move() {
		x = x + dx;
		y = y + dy;
		//System.out.println("Position: (" + x + ", " + y + ") Width/Height: (" + w + ", " + h + ")");
		if (x <= 0) {
			x = 0;
		} else if (x + w >= maxX) {
			x = maxX - w;
		}
		
		if (y <= 0) {
			y = 0;
		} else if (y + h > maxY) {
			y = maxY - h;
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void fire() {
		 miss.add(new Missle(x + (w / 2), y + height / 2));
		 try {
			playSound("/missle_shoot.wav");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public List<Missle> getMissles(){
		return miss;
	}
	
	 public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE) {
        	fire();
        }
        
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = -10;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 10;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = -5;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 5;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
    
	public void playSound(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
	    java.net.URL url = Road.class.getResource(soundFile);
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}
}
