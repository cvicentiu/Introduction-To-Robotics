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

	public Main() {
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				// TODO: Write program logic.
				while (c.x < 450) {
					c.move(1);
					pause(10);
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
		c = new Car(140, 90, 180, CAR_FILENAME);
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