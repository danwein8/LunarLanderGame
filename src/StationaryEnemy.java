import java.awt.*;

/**
 * stationary enemy that just bobs up and down in place, simple obstacle
 * @author danielweiner
 *
 */
public class StationaryEnemy extends Enemy {
	
	static AnimationBk stationarySprite;

	public StationaryEnemy(double x, double y, int w, int h, LunarLander target, int health) {
		super(x, y, w, h, target, health);
	}
	
	@Override
	public void update() {
		moveUp(4);
	}
	
	public void update2() {
		moveDown(4);
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)x, (int)y, w, h);
		g.drawImage(stationarySprite.getImage(), (int)x, (int)y, null);
	}
}
