import java.awt.Graphics;

/**
 * Ammo class that extends Item, allows the player to pick up more bullets
 * during game play to fight off more enemies
 * @author danielweiner
 *
 */
public class Ammo extends Item {
	
	static AnimationBk ammoSprite;
	
	SoundManager sm = new SoundManager();

	public Ammo(double x, double y, int w, int h, int strength) {
		super(x, y, w, h, strength);
	}

	@Override
	public void pickUp(LunarLander lander) {
		lander.addAmmo(getStrength());
		
		sm.setFile(1);
		sm.play();
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)(x), (int)(y), w, h);
		//g.drawString("A", (int)(x + 1), (int)(y + h));
		g.drawImage(ammoSprite.getImage(), (int)x, (int)y, null);
	}	
}