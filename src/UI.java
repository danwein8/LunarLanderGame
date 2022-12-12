import java.awt.Graphics;

public class UI {
	
	static AnimationBk heart;
	static AnimationBk emptyHeart;
	LunarLander lander;
	
	int x;
	int y;

	public UI(int x, int y, LunarLander lander) {
		this.x = x;
		this.y = y;
		this.lander = lander;
	}
	
	public void draw(Graphics g) {
		if (lander.health == 2) {
			g.drawImage(heart.getImage(), (int)x, (int)y, null);
			g.drawImage(heart.getImage(), (int)x + 20, (int)y, null);
		}
		if (lander.health == 1) {
			g.drawImage(heart.getImage(), (int)x, (int)y, null);
			g.drawImage(emptyHeart.getImage(), (int)x + 20, (int)y, null);
		}
		else {
			g.drawImage(emptyHeart.getImage(), (int)x, (int)y, null);
			g.drawImage(emptyHeart.getImage(), (int)x + 20, (int)y, null);
		}
	}

}
