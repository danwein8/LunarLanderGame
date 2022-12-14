import java.awt.*;

public class Projectiles extends Rect {
	
	private final long createdMillis;
	
	static AnimationBk projectileImage;

	public Projectiles(double x, double y, int w, int h) {
		super(x, y, w, h);
		createdMillis = System.currentTimeMillis();
	}
	
	public void launchProjectile() {
		moveRight(30);
	}
	
	public void launchProjectileL() {
		moveLeft(30);
	}
	
	public int getAgeInSeconds() {
		long nowMillis = System.currentTimeMillis();
		return (int)((nowMillis - this.createdMillis) / 1000);
	}
	
	public void draw(Graphics g) {
		//g.drawRect((int)(x), (int)(y), w, h);
		g.drawImage(projectileImage.getImage(), (int)x, (int)y, null);
	}

}
