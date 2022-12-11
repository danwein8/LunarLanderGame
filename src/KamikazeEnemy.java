import java.awt.Graphics;

/**
 * Kamikaze enemies fly straight across the screen towards the player, its their choice whether to 
 * avoid or to kill them
 * @author danielweiner
 *
 */
public class KamikazeEnemy extends Enemy {
	
	static AnimationBk kamikazeSprite;

	public KamikazeEnemy(double x, double y, int w, int h, LunarLander target, int health) {
		super(x, y, w, h, target, health);
	}

	@Override
	public void update() {
		moveLeft(10);
	}
	
	public void update2() {
		
	}
	
	public void draw(Graphics g) {
		g.drawRect((int)x, (int)y, w, h);
		g.drawImage(kamikazeSprite.getImage(), (int)x, (int)y, null);
	}
}
