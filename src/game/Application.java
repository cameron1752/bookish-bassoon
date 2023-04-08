package game;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Application extends JFrame{
	public Application() {
		initUI();
	}
	
	private void initUI() {
		add(new Road());
		
		setTitle("Delivery Driver Simulator 2k23");
		setSize(1000, 1200);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(() -> {
			Application app = new Application();
			app.setVisible(true);
		});
	}

}
