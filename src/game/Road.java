package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Road extends JPanel implements ActionListener {
	private Timer timer;
	private Truck truck;
	private final int DELAY = 1;
	private String imgURL = "src/resources/road.png";
	private Image scaled;
	private List<Package> packages;
	private List<Pedestrian> peds;
	private int collected = 0;
	private int hit = 0;
	private int health = 3;
	private int packNum = 5000;
	private int pedNum = 200;
	private boolean ingame;
	private int destroyedPeds = 0;
	
	private final int B_WIDTH = 1000;
	private final int B_HEIGHT = 1200;
	
	JButton reset = new JButton("Reset");
	JButton quit = new JButton("Quit");
	JPanel back = new JPanel();
	JLabel gameOver = new JLabel("<html><h1><font color='red'>Game Over!</font></h1></html>");
	JLabel score = new JLabel("<html><h1><font color='red'>Packages Collected: </font></h1></html>");
	
	public Road() {
		initRoad();
	}
	
	private void initRoad() {
		addKeyListener(new TAdapter());
		setBackground(Color.gray);
		setFocusable(true);
		
		initBoard();
		
		ingame = true;
		
		hit = 0;
		health = 3;
		collected = 0;
		
		truck = new Truck(400, 700);
		
		Image img;
		ImageIcon icon = new ImageIcon(imgURL); 
		
		img = icon.getImage();
		
	    scaled = img.getScaledInstance(B_WIDTH, B_HEIGHT, Image.SCALE_SMOOTH);
	    
	    initPackages();
	    initPeds();
	    
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	private void initPackages() {
		packages = new ArrayList<>();
		int sY = -1000;

		Random rand = new Random();
		rand.setSeed(486153168);;
		
		for (int i = 0; i < packNum; i++) {
			packages.add(new Package(getRandomNumberInRange(0, 900), (sY * i)));
//			System.out.println("Package #" + i + " (" + packages.get(i).getX() + ", " + packages.get(i).getY() + ")");
		}
		
		System.out.println("Packages to pickup!: " + packages.size());
	}
	
	private void initPeds() {
		peds = new ArrayList<>();
		int sY = -1750;

		Random rand = new Random();
		rand.setSeed(1864865415);;
		
		for (int i = 0; i < pedNum; i++) {
			peds.add(new Pedestrian(getRandomNumberInRange(0, 900), (sY * i) - 100));
//			System.out.println("Pedestrian #" + i + " (" + peds.get(i).getX() + ", " + peds.get(i).getY() + ")");
		}
		
		System.out.println("Pedestrians to avoid!: " + peds.size());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		if (ingame) {
			super.paintComponent(g);
		    
			doDrawing(g);
		}else {
			try {
				drawGameOver(g);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(scaled, 1, 1, this);
		
		for (Package p : packages) {
			if (p.isVisible()) {
				g2d.drawImage(p.getImage(), p.getX(), p.getY(), this);
			}
		}
		
		for (Pedestrian p : peds) {
			if (p.isVisible()) {
				g2d.drawImage(p.getImage(), p.getX(), p.getY(), this);
			}
		}
		
		List<Missle> miss = truck.getMissles();
		
		for (Missle m : miss) {
			if (m.isVisible()) {
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}
		
		g2d.drawImage(truck.getImage(), truck.getX(), truck.getY(), this);
		
		g.setColor(Color.WHITE);
        g.drawString("Packages collected: " + collected, 5, 15);
        g.drawString("Health Left: " + (health - hit), 5, 30);
        g.drawString("Obstacles Destroyed: " + destroyedPeds, 5, 45);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();
		
		step();
		try {
			checkCollisions();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		repaint();
	}
	
	private void step() {
		truck.move();
		
		repaint(truck.getX()-1, truck.getY()-1, truck.getWidth()+2, truck.getHeight()+2);
		
		
	}
	
	public void updatePackages() {
		if (packages.isEmpty()) {
			ingame = false;
			return;
		}
		
		for (int i = 0; i < packages.size(); i++) {
			Package p = packages.get(i);
			
			if (p.isVisible()) {
				p.move(collected);
			} else {
				packages.remove(i);
			}
		}
	}
	
	public void updatePeds() {
		if (peds.isEmpty()) {
			ingame = false;
			return;
		}
		
		for (int i = 0; i < peds.size(); i++) {
			Pedestrian p = peds.get(i);
			
			if (p.isVisible()) {
				p.move(collected);
			} else {
				packages.remove(i);
			}
		}
	}
	
	public void checkCollisions() throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		Rectangle r = new Rectangle(truck.getX(), truck.getY(), truck.getWidth(), truck.getHeight());
		
		for (int i = 0; i < packages.size(); i++) {
			
			packages.get(i).move(collected);
			Rectangle r2 = new Rectangle(packages.get(i).getX(), packages.get(i).getY(), packages.get(i).getWidth(), packages.get(i).getHeight());
			
			if (r2.getY() >= 0 && r2.getY() < 1400) {
				if (r2.intersects(r) || r2.intersects(r)) {
					packages.remove(i);
					collected = collected + 1;
					playSound("ding.wav");
				} 
			} 
			
		}
		
		for (int i = 0; i < peds.size(); i++) {
					
			peds.get(i).move(collected);
			Rectangle r2 = new Rectangle(peds.get(i).getX(), peds.get(i).getY(), peds.get(i).getWidth(), peds.get(i).getHeight());
			
			if (r2.getY() >= 0 && r2.getY() < 1400) {
				if (r2.intersects(r) || r2.intersects(r)) {
					peds.remove(i);
					hit = hit + 1;
					playSound("hit.wav");
				} 
			} 
			
			if (hit >= health) {
				ingame = false;
				inGame();
			}
		}
		
		List<Missle> miss = truck.getMissles();
		
		for (int i = 0; i < miss.size(); i++) {
			Rectangle rM = new Rectangle(miss.get(i).getX(), miss.get(i).getY(), miss.get(i).getWidth(), miss.get(i).getHeight());
			
			if (miss.get(i).getY() < 0) {
				miss.remove(i);
			} else {
				for (int k = 0; k < peds.size(); k++) {
					Rectangle rP = new Rectangle(peds.get(k).getX(), peds.get(k).getY(), peds.get(k).getWidth(), peds.get(k).getHeight());
					
					if (rM.intersects(rP) || rP.intersects(rM)) {
						peds.remove(k);
						miss.get(i).setVisible(false);
						destroyedPeds = destroyedPeds + 1;
						playSound("destroyed_ped.wav");
					}
					
				}
				miss.get(i).move(collected);
			}
		}
	}
	
	private void drawGameOver(Graphics g) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		score.setText("<html><h1><font color='red'>Packages Collected: " + collected + "</font></h1></html>");
		back.setVisible(true);
        
		playSound("gameover.wav");
    }
	
	private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }
	private class TAdapter extends KeyAdapter{
		@Override
        public void keyReleased(KeyEvent e) {
            truck.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            truck.keyPressed(e);
        }
	}
	
	public void playSound(String soundFile) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
	    File f = new File("./src/resources/" + soundFile);
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public void initBoard() {
		reset.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
		    	initRoad();
		    }
		});
		
		quit.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
		    	System.exit(0);
		    }
		});
		
		back.setVisible(false);
		setLayout(new GridBagLayout());
		back.setLayout(new BoxLayout(back, BoxLayout.Y_AXIS));
		back.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		gameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.add(gameOver);
		score.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.add(score);
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.add(reset);
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.add(quit);
		add(back, new GridBagConstraints());
	}
}
