import java.awt.*;
import java.util.*;

public class LunarLander extends Rect 
{
	
	// Acceleration
	double ax;
	double ay;
	
	public static final float SPEED = .3f;
	public static final float GRAVITY = .002f;
	
	// a sprite for the lander
	AnimationBk landerImage;
	
	// flame animation for under the lander when it accelerates / lifts
	AnimationBk flame;
	
	// dust cloud animation for collision with anything other than the landing pad
	AnimationBk crash;
	
	// weight that gets lighter making the lander more responsive as the fuel runs out
	double weight;
	// fuel, runs out every time you accelerate
	double fuel;
	int bullets;
	int health;
	
	ArrayList<Projectiles> projectiles = new ArrayList<Projectiles>();
	
	SoundManager sm = new SoundManager();
	
	
	public LunarLander(double x, double y, int w, int h, double weight, double fuel, int bullets, int health) {
		super(x, y, w, h);
		
		this.weight = weight;
		this.fuel = fuel;
		this.bullets = bullets;
		this.health = health;
		
		// make new flame and crash animations when you get the sprite 
	}
	
	public void update(long elapsedTime) {
		gravityAccel(GRAVITY);
		moveBasedOnPhysics();
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	// set the weight based on the remaining fuel, less fuel means lighter lander means
	// more responsive lift and moving left and right
	public double getCurrentWeight(double weight, double fuel) {
		// TODO: FUEL / 1.0 IS JUST FUEL
		return weight + (fuel / 1.0);
	}
	
	public double getFuel() {
		return this.fuel;
	}
	
	public void addFuel(double fuelIn) {
		fuel += fuelIn;
	}
	
	public int getAmmo() {
		return this.bullets;
	}
	
	public void addAmmo(int ammoIn) {
		bullets += ammoIn;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void addHealth(int healthIn) {
		health += healthIn;
	}
	
	public void shoot()
	{
		projectiles.add(new Projectiles(this.x + (this.w / 2), this.y, 5, 5));
		
		bullets--;
		sm.setFile(3);
		sm.play();
	}
	
	public ArrayList<Projectiles> getProjectiles()
	{
		return projectiles;
	}
	
	public void removeProjectile(int index)
	{
		projectiles.remove(index);
	}
	
	// burn fuel when maneuvering the lander in any direction
	public void burnFuel() {
		// burn one sixtieth of fuel at a time because its called 60 times a second, should have 100
		// seconds of total flight/maneuvering time
		//double oneSixtiethSec = 0.02;
		
		/**
		 * burning one sixtieth of fuel at a time was giving too much time, one tenth at a time puts
		 * much more pressure on the player to make snap decisions
		 */
		double oneTenthSec = 0.1;
		this.fuel -= oneTenthSec;
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
		//vx = -dx;
		x -= dx;
	}
	
	public void moveRight(double dx) {
		//vx = dx;
		x += dx;
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
	
	public void setFlameAnim(AnimationBk animIn) {
		this.flame = animIn;
	}
	
	public void setCrashAnim(AnimationBk animIn) {
		this.crash = animIn;
	}
	
	public void drawCrash(Graphics g) {
		g.drawImage(crash.getImage(), (int)x, (int)y, null);
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)(x), (int)(y), w, h);
		g.drawImage(landerImage.getImage(), (int)x, (int)y, null);
		g.drawImage(flame.getImage(), (int)x + 3, (int)y + h + 6, null);
	}
	
}
