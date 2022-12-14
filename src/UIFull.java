import java.awt.Graphics;

public class UIFull extends UI {
	
	static AnimationBk heart;

	public UIFull(int x, int y, LunarLander lander) {
		super(x, y, lander);
	}
	
	public void draw(Graphics g) {
		//g.drawRect(x+Camera.x, y+Camera.y, 20, 20);
		g.drawImage(heart.getImage(), x+Camera.x, y+Camera.y, null);
	}

}
