import java.util.ArrayList;
import java.util.LinkedList;


public class CarDriver {
	CarSimulator sim;
	ArrayList<Vector2D> goals;
	ArrayList<Vector2D> origGoals;
	int currentGoal = 0;
	public CarDriver(CarSimulator sim) {
		this.sim = sim;
	}
	
	public void setGoalList(ArrayList<Vector2D> goals) {
		
		this.currentGoal = 0;
		this.origGoals = goals;
		ArrayList<Vector2D> adjustedGoals = new ArrayList<>();
		for (int i = 0; i < goals.size(); i++) {
			int prev = i - 1;
			int next = i + 1;
			if (prev < 0)
				prev = goals.size() - 1;
			if (next == goals.size())
				next = 0;
			
			
			Vector2D currG = goals.get(i);
			Vector2D prevG = goals.get(prev);
			Vector2D nextG = goals.get(next);
			
			if (currG.y - prevG.y > 0 && Math.abs(currG.y - prevG.y) > 1) {
				if (nextG.x - currG.x > 0 && Math.abs(nextG.x - currG.x) > 1) {
					adjustedGoals.add(new Vector2D(currG.x - (Terrain.roadWidth / 4), currG.y + (Terrain.roadWidth / 4)));
				} else {
					adjustedGoals.add(new Vector2D(currG.x - (Terrain.roadWidth / 4), currG.y - (Terrain.roadWidth / 4)));
				}
			}
			else if (currG.y - prevG.y < 0 && Math.abs(currG.y - prevG.y) > 1) {
				if (nextG.x - currG.x > 0 && Math.abs(nextG.x - currG.x) > 1) {
					adjustedGoals.add(new Vector2D(currG.x + (Terrain.roadWidth / 4), currG.y + (Terrain.roadWidth / 4)));
				} else {
					adjustedGoals.add(new Vector2D(currG.x + (Terrain.roadWidth / 4), currG.y - (Terrain.roadWidth / 4)));
				}
			}
			else if (currG.x - prevG.x < 0 && Math.abs(currG.x - prevG.x) > 1) {
				if (nextG.y - currG.y > 0 && Math.abs(nextG.y - currG.y) > 1) {
					adjustedGoals.add(new Vector2D(currG.x - (Terrain.roadWidth / 4), currG.y - (Terrain.roadWidth / 4)));
				} else {
					adjustedGoals.add(new Vector2D(currG.x + (Terrain.roadWidth / 4), currG.y - (Terrain.roadWidth / 4)));
				}
			}
			else if (currG.x - prevG.x > 0 && Math.abs(currG.x - prevG.x) > 1) {
				if (nextG.y - currG.y > 0 && Math.abs(nextG.y - currG.y) > 1) {
					adjustedGoals.add(new Vector2D(currG.x - (Terrain.roadWidth / 4), currG.y + (Terrain.roadWidth / 4)));
				} else {
					adjustedGoals.add(new Vector2D(currG.x + (Terrain.roadWidth / 4), currG.y + (Terrain.roadWidth / 4)));
				}
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
		//System.out.println(Math.toDegrees(angle));
		double angleDeg = Math.toDegrees(angle);
		
		double throttle = 1;
		double dist = Math.abs(line.x) + Math.abs(line.y);
		double targetSpeed = sim.maxSpeed;
		if ( dist < 125) {
			targetSpeed = sim.maxSpeed * 0.5;
		}

		boolean collision = false;
		int number = -1;
		
		int[] skipI = {0, 0, 3, 3};
		int[] skipJ = {0, 3, 0, 3};
		for (int i = 0; i < Terrain.inIntersection.length; i++) {
			for (int j = 0; j < Terrain.inIntersection[i].length; j++) {
				boolean skip = false;
				for (int k = 0; k < skipI.length; k++) {
					if (i == skipI[k] && j == skipJ[k])
						skip = true;
				}
				if (skip)
					continue;
				LinkedList<CarSimulator> l = Terrain.inIntersection[i][j];
				if (l.contains(sim) && l.get(0) != sim) {
					targetSpeed = 0;
				}
			}
		}
		
		if (sim.speed > targetSpeed) {
			throttle = -1;
		} else {
			throttle = +1;
		}
		

		
		//System.out.println(sim.speed + " " + throttle);
		if ((angleDeg > 7 && angleDeg < 180) || angleDeg < -187) {
			//System.out.println("Right");
			sim.giveCommand(1, throttle);
		} else if ((angleDeg < - 7 && angleDeg > -180) || angleDeg > 187){
			//System.out.println("Left");
			sim.giveCommand(-1, throttle);
		} else {
			sim.steeringAmplitude = 0;
			sim.giveCommand(0, throttle);
		}
	}
}
