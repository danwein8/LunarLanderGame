import java.awt.Graphics;

/**
 * this enemy tries to block most of the tunnel by constantly straight flying up and down
 * @author danielweiner
 *
 */
public class UpandDownEnemy extends Enemy {
	
	int movement;

	public UpandDownEnemy(double x, double y, int w, int h, LunarLander target, int health, int movementIn) {
		super(x, y, w, h, target, health);
		this.movement = movementIn;
	}

	@Override
	public void update() {
		moveBy(0, movement);
	}
	
	public void update2() {
		
	}
	
	public void negateMovement() {
		movement *= -1;
	}
	
	public void setMovement(int newMovement) {
		movement = newMovement;
	}
	
	public int getMovement() {
		return movement;
	}
	
	public void draw(Graphics g) {
		g.drawRect((int)x, (int)y, w, h);
	}
}
