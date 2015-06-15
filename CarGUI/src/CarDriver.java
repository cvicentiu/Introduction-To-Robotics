
public class CarDriver {
	CarSimulator sim;
	Vector2D goal;
	public CarDriver(CarSimulator sim) {
		this.sim = sim;
	}
	
	public void setGoal(Vector2D position) {
		goal = position;
	}
	
	public void controlCar() {
		Vector2D line = new Vector2D(sim.position);
		line.x = goal.x - line.x;
		line.y = goal.y - line.y;
		
		double angle = Math.atan2(line.y, line.x) - Math.atan2(sim.forward.y, sim.forward.x);
		System.out.println(Math.toDegrees(angle));
		double angleDeg = Math.toDegrees(angle);
		if (angleDeg > 0 && angleDeg < 180 || angleDeg < -180) {
			System.out.println("Right");
			sim.giveCommand(1, 1);
		} else {
			System.out.println("Left");
			sim.giveCommand(-1, 1);
		}
	}
}
