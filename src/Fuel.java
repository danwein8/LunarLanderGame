import java.awt.Graphics;

/**
 * Fuel class that extends Item, allows the player to fill the lander up
 * with more fuel during the game, however when the fuel fills up so suddenly
 * the lander will start to handle much less precisely so it adds an extra layer 
 * of difficulty and strategy to the game. Should you take this fuel container or
 * try to hold out for the next one?
 * @author danielweiner
 *
 */
public class Fuel extends Item {
	
	static AnimationBk fuelSprite;
	
	SoundManager sm = new SoundManager();

	public Fuel(double x, double y, int w, int h, int strength) {
		super(x, y, w, h, strength);
	}

	@Override
	public void pickUp(LunarLander lander) {
		lander.addFuel(getStrength());
		
		sm.setFile(1);
		sm.play();
		
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)(x), (int)(y), w, h);
		//g.drawString("F", (int)(x + 1), (int)(y + h));
		g.drawImage(fuelSprite.getImage(), (int)x, (int)y, null);
	}	
}
