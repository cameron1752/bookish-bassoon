package game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Missle extends Sprite {

	public Missle(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		loadImage();
	}
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
			private String imgPath = "/missile.png";
			
			
			
			
			private void loadImage() {
				java.net.URL url = Road.class.getResource(imgPath);
				ImageIcon imgI = new ImageIcon(url);
				image = imgI.getImage();
				
				w = image.getWidth(null);
				h = image.getHeight(null);
			}
			
			public void move(int collected) {
				dy = -10;
					
				
				
				x = x + dx;
				y = y + dy;
				
				
//				System.out.println("Package Position: (" + x + ", " + y + ") Width/Height: (" + w + ", " + h + ")");
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
