
class Vector2D {
	public double x, y;
	public Vector2D(){
		x = 0;
		y = 0;
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
}

public class CarSimulator {
	
	public CarSimulator(Vector2D position, Vector2D forward) {
		this.position = position;
		this.forward = forward;
	}
	// Current position parameters.
	public Vector2D position;
	public Vector2D forward;

	// Current commands.
	double throttle = 0; // -1 (full brake, acceleration is negative)
					 // 0 (no throttle, acceleration is 0)
    				 // 1 (full throttle, acceleration positive)
	double steeringForce = 0;
	
	// Current dynamic parameters.
	double speed = 0;    //0 (stopped) 1 (at maxSpeed)
	double steeringAmplitude = 0; // -1 (full left) .. 0 (center) .. 1 (full right)
	 
	
	// Limitations.
	double maxSpeed = 100;
	double maxSteeringAngle = Math.toRadians(2);
	double sideToSideSteeringTime = 3000;
	double maxAcceleration = 20;
	double maxDecceleration = 40;
	

	public void giveCommand(double steeringForce, double throttle) {
		this.steeringForce = Math.signum(steeringForce) * Math.min(Math.abs(steeringForce), 1);
		this.throttle = Math.signum(throttle) * Math.min(Math.abs(throttle), 1);
	}
	
	public void executeCommand(double timeDeltaMs) {
		double timeToFullTurn = sideToSideSteeringTime / steeringForce;		
 		double steeringDiff = 2 * (timeDeltaMs / timeToFullTurn);
 		if (steeringDiff == Double.NaN)
 			steeringDiff = 0;
		steeringAmplitude = steeringAmplitude + steeringDiff;
		// Clamp.
		if (steeringAmplitude > 1)
			steeringAmplitude = 1;
		if (steeringAmplitude < -1)
			steeringAmplitude = -1;
		
		if (throttle > 0) {
			speed += (timeDeltaMs / 1000) * maxAcceleration * throttle;
			speed = Math.min(maxSpeed, speed);
		}
		else {
			speed += (timeDeltaMs / 1000) * maxDecceleration * throttle;
			speed = Math.max(speed, 0);
		}
		
		double oldX = forward.x;
		double oldY = forward.y;
		double cos = Math.cos(steeringAmplitude * maxSteeringAngle * speed / maxSpeed);
		double sin = Math.sin(steeringAmplitude * maxSteeringAngle * speed / maxSpeed);
		forward.x = oldX * cos - oldY * sin;
		forward.y = oldX * sin + oldY * cos;
		
		position.x += forward.x * speed * timeDeltaMs / 1000;
		position.y += forward.y * speed * timeDeltaMs / 1000;
	}
	
}
