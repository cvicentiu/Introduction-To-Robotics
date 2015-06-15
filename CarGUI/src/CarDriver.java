import java.util.ArrayList;


public class CarDriver {
	CarSimulator sim;
	ArrayList<Vector2D> goals;
	int currentGoal = 0;
	public CarDriver(CarSimulator sim) {
		this.sim = sim;
	}
	
	public void setGoalList(ArrayList<Vector2D> goals) {
		
		this.currentGoal = 0;
		ArrayList<Vector2D> adjustedGoals = new ArrayList<>();
		for (int i = 0; i < goals.size(); i++) {
			int prev = i - 1;
			if (prev < 0)
				prev = goals.size() - 1;
			
			Vector2D currG = goals.get(i);
			Vector2D prevG = goals.get(prev);
			
			if (currG.y - prevG.y > 0 && Math.abs(currG.y - prevG.y) > 1) {
				adjustedGoals.add(new Vector2D(currG.x - (Terrain.roadWidth / 4), currG.y - (Terrain.roadWidth / 4)));
			}
			
			else
				adjustedGoals.add(currG);
		
		}
		this.goals = adjustedGoals;	
	}
	
	public void controlCar() {
		Vector2D line = new Vector2D(sim.position);
		line.x = goals.get(currentGoal).x - line.x;
		line.y = goals.get(currentGoal).y - line.y;
		
		if (Math.abs(line.x) + Math.abs(line.y) < 35) {
			currentGoal += 1;
			currentGoal = currentGoal % goals.size();
		}
		
		double angle = Math.atan2(line.y, line.x) - Math.atan2(sim.forward.y, sim.forward.x);
		System.out.println(Math.toDegrees(angle));
		double angleDeg = Math.toDegrees(angle);
		
		double speed = 1;
		if (Math.abs(line.x) + Math.abs(line.y) < 100) {
			speed = -0.5;
		}
		
		System.out.println(speed);
		if ((angleDeg > 7 && angleDeg < 180) || angleDeg < -187) {
			System.out.println("Right");
			sim.giveCommand(1, speed);
		} else if ((angleDeg < - 7 && angleDeg > -180) || angleDeg > 187){
			System.out.println("Left");
			sim.giveCommand(-1, speed);
		} else {
			sim.steeringAmplitude = 0;
			sim.giveCommand(0, speed);
		}
	}
}
