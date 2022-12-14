import java.awt.Graphics;

public class Health extends Item {

	static AnimationBk healthSprite;
	
	SoundManager sm = new SoundManager();
	
	public Health(double x, double y, int w, int h, int strength) {
		super(x, y, w, h, strength);
		
	}

	@Override
	public void pickUp(LunarLander lander) {
		if (lander.health < 2) lander.addHealth(strength);
		
		sm.setFile(1);
		sm.play();
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)(x), (int)(y), w, h);
		//g.drawString("+", (int)(x + 1), (int)(y + h));
		g.drawImage(healthSprite.getImage(), (int)x, (int)y, null);
	}	
}
