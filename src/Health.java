import java.awt.Graphics;
import java.awt.Image;

public class Health extends Item {

	static AnimationBk healthSprite;
	
	public Health(double x, double y, int w, int h, int strength) {
		super(x, y, w, h, strength);
	}

	@Override
	public void pickUp(LunarLander lander) {
		lander.addHealth(h);
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)(x), (int)(y), w, h);
		//g.drawString("+", (int)(x + 1), (int)(y + h));
		g.drawImage(healthSprite.getImage(), (int)x, (int)y, null);
	}	
}
