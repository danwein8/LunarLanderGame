import java.awt.Graphics;
import java.awt.Image;

public class Health extends Item {

	public Health(double x, double y, int w, int h, int strength, Image sprite) {
		super(x, y, w, h, strength, sprite);
	}

	@Override
	public void pickUp(LunarLander lander) {
		lander.addHealth(h);
	}
	
	public void draw(Graphics g) {
		g.drawRect((int)(x), (int)(y), w, h);
		g.drawString("+", (int)(x + 1), (int)(y + h));
	}	
}
