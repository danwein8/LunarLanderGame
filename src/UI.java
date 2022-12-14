import java.awt.Graphics;

public class UI {

	AnimationBk image;
	LunarLander lander;
	
	int x;
	int y;

	public UI(int x, int y, LunarLander lander) {
		this.x = x;
		this.y = y;
		this.lander = lander;
	}
	
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x+Camera.x, y+Camera.y, null);
	}

}
