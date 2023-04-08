package game;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Package extends Sprite {
	// amount to move x and y direction
		private int dx = 0;
		private int dy = 1;
		
		// dimensions of screen to ensure we aren't falling off the road
		private int maxX = 1000;
		private int maxY = 1200;
		
		// image height and width
		private int w;
		private int h;
		
		// image object
		private Image image;
		
		// image uri
		private String imgPath = "/package.png";
		
		
		public Package(int x, int y) {
			super(x, y);
			
			loadImage();
		}
		
		private void loadImage() {
			java.net.URL url = Road.class.getResource(imgPath);
			ImageIcon imgI = new ImageIcon(url);
			image = imgI.getImage();
			
			w = image.getWidth(null);
			h = image.getHeight(null);
		}
		
		public void move(int collected) {
			if (collected < 5) {
				dy = 5;
			} else if (collected >= 5 && collected < 25) {
				dy = 8;
			} else if (collected >= 25 && collected < 50) {
				dy = 15;
			} else if (collected >= 50 && collected < 75) {
				dy = 20;
			} else if (collected >= 75) {
				dy = 35;
			}
				
			
			
			x = x + dx;
			y = y + dy;
			
			if (y > 1200) {
				y = 0;
			}
//			System.out.println("Package Position: (" + x + ", " + y + ") Width/Height: (" + w + ", " + h + ")");
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
		

}
