import java.awt.*;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {
	public final static int CANVAS_WIDTH = 580;
	public final static int CANVAS_HEIGHT = 550;
	public final static String TITLE = "Introduction to Robotics - Car";
	public final static String TERRAIN_FILENAME = "track.jpg";
	public final static String CAR_FILENAME = "beetle-red.gif";

	public static Terrain t;
	public static Car c;
	public static CarSimulator sim = new CarSimulator(new Vector2D(300, 300), new Vector2D(1, 0));
	
	public Main() {
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				int frame = 0;
				
				while (true) {
					frame++;
					sim.giveCommand(Math.signum(Math.sin((frame- 1) / 88.0f) - Math.sin(frame / 88.0f)), 1);
					pause(10);					
					sim.executeCommand(10);
					
					c.x = (int) Math.round(sim.position.x);
					c.y = (int) Math.round(sim.position.y);
					c.direction = Math.PI + Math.atan2(sim.forward.y, sim.forward.x);
					repaint();
				}
			};
		};
		animationThread.start(); // start the thread to run animation

		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	}

	/** Custom painting codes on this JPanel */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // paint background

		/* TODO drawings */
		t.draw(g);
		c.draw(g);
	}

	public static void init() throws IOException {
		t = new Terrain(TERRAIN_FILENAME);
		c = new Car(100, 90, 0, CAR_FILENAME);
	}

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The entry main() method
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		init();

		pause(100);

		/* Render the frame */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(TITLE);
				frame.setContentPane(new Main());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
