import java.awt.*;

/**
 * pickup items like fuel and ammo that you can find scattered throughout the level, abstract class
 * meant to be inherited by the actual item classes
 * @author danielweiner
 *
 */
public abstract class Item extends Rect {
	
	/**
	 * how much of its resource it restores
	 */
	int strength;
	/**
	 * the sprite used for this item
	 */
	Image sprite;
	
	public Item(double x, double y, int w, int h, int strength, Image sprite) {
		super(x, y, w, h);
		this.strength = strength;
		this.sprite = sprite;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public Image getSprite() {
		return this.sprite;
	}
	
	/**
	 * the effect the Item has when it is picked up by the lander
	 */
	public abstract void pickUp(LunarLander lander);
	
	public void draw(Graphics g) {
		g.drawRect((int)(x), (int)(y), w, h);
	}
	
}
