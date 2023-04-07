package game;

import java.awt.Image;
import java.awt.event.KeyEvent;

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
	private String imgPath = "src/resources/truck.png";
	
	
	public Truck(int x, int y){
		super(x, y);
		
		initTruck();
	}
	
	private void initTruck() {
		loadImage();
	}
	
	private void loadImage() {
		ImageIcon imgI = new ImageIcon(imgPath);
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
	
	 public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

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
}
