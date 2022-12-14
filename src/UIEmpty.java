import java.awt.Graphics;

public class UIEmpty extends UI {
	
	static AnimationBk emptyHeart;

	public UIEmpty(int x, int y, LunarLander lander) {
		super(x, y, lander);
	}
	
	public void draw(Graphics g) {
		//g.drawRect(x+Camera.x, y+Camera.y, 20, 20);
		g.drawImage(emptyHeart.getImage(), x+Camera.x, y+Camera.y, null);
	}

}
