import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {
	public static int CANVAS_WIDTH = 500;
	public static int CANVAS_HEIGHT = 500;
	public static String TITLE = "Introduction to Robotics - Car";

	public Main() {
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				// TODO: Write program logic.
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
	}

	public static void init() {
	}

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** The entry main() method */
	public static void main(String[] args) {
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