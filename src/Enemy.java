import java.awt.Image;

/**
 * a basic enemy class that is going to try to collide with the lunar lander
 * @author danielweiner
 *
 */
public class Enemy extends Rect {

	// a sprite for the lander
	Image enemySprite;

	// flame animation for under the lander when it accelerates / lifts
	Animation[] flame;

	// dust cloud animation for collision with anything other than the landing pad
	Animation[] crash;
	
	/**
	 * the enemy can either be stationary, fly up and down trying to block the path, or can be a
	 * kamikaze enemy that tries to collide with the lander directly. This kamikaze enemy will take
	 * in the LunarLander and it will "chase" it but it will only go one direction so if it misses
	 * the player it will keep going and the player will be safe.
	 */
	LunarLander target;

	public Enemy(double x, double y, int w, int h, LunarLander target) {
		super(x, y, w, h);
		this.target = target;
	}

}
