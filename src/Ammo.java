import java.awt.Graphics;
import java.awt.Image;
import java.util.*;

/**
 * Ammo class that extends Item, allows the player to pick up more bullets
 * during game play to fight off more enemies
 * @author danielweiner
 *
 */
public class Ammo extends Item {

	public Ammo(double x, double y, int w, int h, int strength, Image sprite) {
		super(x, y, w, h, strength, sprite);
	}

	@Override
	public void pickUp(LunarLander lander) {
		lander.setAmmo(getStrength());
	}
	
	public void draw(Graphics g) {
		g.drawRect((int)(x), (int)(y), w, h);
		g.drawString("A", (int)(x + 1), (int)(y + h));
	}	
}