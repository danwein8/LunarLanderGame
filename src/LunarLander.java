import java.awt.*;

public class LunarLander extends Rect {
	
	// Acceleration
	double ax;
	double ay;
	
	// flame animation for under the lander when it accelerates / lifts
	Animation[] flame;
	
	// dust cloud animation for collision with anything other than the landing pad
	Animation[] crash;
	
	// need an image for the lander, maybe store in Animation array and just get single image
	
	// weight that gets lighter making the lander more responsive as the fuel runs out
	double weight;
	// fuel, runs out every time you accelerate
	double fuel;
	
	
	public LunarLander(double x, double y, int w, int h, double weight, double fuel) {
		super(x, y, w, h);
		
		this.weight = weight;
		this.fuel = fuel;
		
		// make new flame and crash animations when you get the sprite 
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	// set the weight based on the remaining fuel, less fuel means lighter lander means
	// more responsive lift and moving left and right
	public double getCurrentWeight(double weight, double fuel) {
		return weight + (fuel / 1.0);
	}
	
	public double getFuel() {
		return this.fuel;
	}
	
	// burn fuel when maneuvering the lander in any direction
	public void burnFuel() {
		// burn one sixtieth of fuel at a time because its called 60 times a second, should have 100
		// seconds of total flight/maneuvering time
		double oneSixtiethSec = 0.02;
		this.fuel -= oneSixtiethSec;
	}
	
	public void lift(double vy) {
		this.vy = -vy;
	}
	
	public void setAcceleration(double ax, double ay) {
		this.ax = ax;
		this.ay = ay;
	}
	
	public void moveBasedOnPhysics() {
		x += vx;
		y += vy;
		
		vx += ax;
		vy += ay;
		
		if (vx > 0.001) vx *= 0.98;
		if (vx < -0.001) vx *= 0.98;
	}
	
	public void moveLeft(double dx) {
		vx = -dx;
	}
	
	public void moveRight(double dx) {
		vx = dx;
	}
	
	/** 
	 * can I use acceleration instead of velocity for movement to make a more natural 
	 * "outer space" feeling movement where the lander keeps moving in the direction you
	 * were just moving, slowly decelerating.
	 */
	
	public void liftAccel(double dy) {
		ay -= dy;
	}
	
	public void moveLeftAccel(double dx) {
		ax -= dx;
	}
	
	public void moveRightAccel(double dx) {
		ax += dx;
	}
	
	public void gravityAccel(double dy) {
		ay += dy;
	}
	
	public void sideDecel(double dx) {
		ax *= dx;
	}
	
	public void draw(Graphics g) {
		g.drawRect((int)x, (int)y, w, h);
	}
	
}
