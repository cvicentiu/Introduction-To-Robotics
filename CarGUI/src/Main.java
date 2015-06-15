import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Main extends JPanel {
	public final static int CANVAS_WIDTH = 600;
	public final static int CANVAS_HEIGHT = 600;
	public final static String TITLE = "Introduction to Robotics - Car";
	public final static String CAR_FILENAME = "beetle-red.gif";

	public static Terrain t;
	public static Car c[] = new Car[5];
	public static CarSimulator[] sim = new CarSimulator[5];
	public static CarDriver[] driver = new CarDriver[5];
	
	public Main() {
		sim[0] = new CarSimulator(new Vector2D(50 + Terrain.roadWidth / 4, 50 + Terrain.roadWidth / 4), new Vector2D(1, 0));
		driver[0] = new CarDriver(sim[0]);
		sim[1] = new CarSimulator(new Vector2D(50 + 2 * 500 / 3 + Terrain.roadWidth / 4, 50 - Terrain.roadWidth / 4),
								  new Vector2D(-1, 0));
		driver[1] = new CarDriver(sim[1]);
		sim[2] = new CarSimulator(new Vector2D(50 + 3 * 500 / 3 - Terrain.roadWidth / 4,
								  50 + 3 * 500 / 3 -  Terrain.roadWidth / 4), new Vector2D(-1, 0));
		driver[2] = new CarDriver(sim[2]);
		sim[3] = new CarSimulator(new Vector2D(50 + 1 * 500 / 3 + Terrain.roadWidth / 4,
				                               50 + 3 * 500 / 3 - Terrain.roadWidth / 4), new Vector2D(0, -1));
		driver[3] = new CarDriver(sim[3]);
		sim[4] = new CarSimulator(new Vector2D(50 + 2 * 500 / 3 - Terrain.roadWidth / 4,
											   50 + 2 * 500 / 3 - Terrain.roadWidth / 4), new Vector2D(0, 1));
		driver[4] = new CarDriver(sim[4]);
		
		ArrayList<Vector2D> goals = new ArrayList<>();
		goals.add(t.map[0][0].point);
		goals.add(t.map[0][1].point);
		goals.add(t.map[1][1].point);
		goals.add(t.map[2][1].point);
		goals.add(t.map[3][1].point);
		goals.add(t.map[3][0].point);
		goals.add(t.map[2][0].point);
		goals.add(t.map[1][0].point);
		driver[0].setGoalList(goals);
		
		goals = new ArrayList<>();
		goals.add(t.map[0][2].point);
		goals.add(t.map[0][1].point);
		goals.add(t.map[1][1].point);
		goals.add(t.map[1][2].point);
		driver[1].setGoalList(goals);
		
		goals = new ArrayList<>();
		goals.add(t.map[3][3].point);
		goals.add(t.map[3][2].point);
		goals.add(t.map[3][1].point);
		goals.add(t.map[3][0].point);
		goals.add(t.map[2][0].point);
		goals.add(t.map[1][0].point);
		goals.add(t.map[0][0].point);
		goals.add(t.map[0][1].point);
		goals.add(t.map[0][2].point);
		goals.add(t.map[0][3].point);
		goals.add(t.map[1][3].point);
		goals.add(t.map[2][3].point);
		driver[2].setGoalList(goals);
		
		goals = new ArrayList<>();
		goals.add(t.map[3][1].point);
		goals.add(t.map[2][1].point);
		goals.add(t.map[1][1].point);
		goals.add(t.map[1][2].point);
		goals.add(t.map[2][2].point);
		goals.add(t.map[3][2].point);
		driver[3].setGoalList(goals);
		
		goals = new ArrayList<>();
		goals.add(t.map[2][2].point);
		goals.add(t.map[3][2].point);
		goals.add(t.map[3][3].point);
		goals.add(t.map[2][3].point);
		goals.add(t.map[1][3].point);
		goals.add(t.map[0][3].point);
		goals.add(t.map[0][2].point);
		goals.add(t.map[0][1].point);
		goals.add(t.map[0][0].point);
		goals.add(t.map[1][0].point);
		goals.add(t.map[1][1].point);
		goals.add(t.map[1][2].point);
		driver[4].setGoalList(goals);
		Thread animationThread = new Thread() {
			@Override
			public void run() {
				int frame = 0;
				
				long last = System.currentTimeMillis();
				
				int step = 0;
				while (true) {
					
					frame++;
					
					for (int i = 0 ; i < driver.length; i++)
						driver[i].controlCar();
					pause(10);
					for (int i = 0 ; i < driver.length; i++)
						sim[i].executeCommand(10);
					//long current = System.currentTimeMillis();
					
					
					for (int i = 0 ; i < c.length; i++) {
						c[i].x = (int) Math.round(sim[i].position.x);
						c[i].y = (int) Math.round(sim[i].position.y);
						c[i].direction = Math.PI + Math.atan2(sim[i].forward.y, sim[i].forward.x);
					}
					repaint();
					//last = current;
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
		g.setColor(Color.YELLOW);
		for (int i = 0; i < driver.length; i++) {
			g.drawLine((int)sim[i].position.x, (int)sim[i].position.y,
					(int)driver[i].goals.get(driver[i].currentGoal).x,
					(int)driver[i].goals.get(driver[i].currentGoal).y);
		}
		for (int i = 0; i < driver.length; i++) {
			c[i].draw(g);	
		}
		
		
	}

	public static void init() throws IOException {
		t = new Terrain();
		for (int i = 0 ; i < c.length; i++)
			c[i] = new Car(100, 90, 0, CAR_FILENAME);
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
