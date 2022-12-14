import java.awt.Graphics;

public class EnemyProjectiles extends Rect {
	
	private final long createdMillis;
	
	static AnimationBk enemyProjectileImage;

	public EnemyProjectiles(double x, double y, int w, int h) {
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
		g.drawImage(enemyProjectileImage.getImage(), (int)x, (int)y, null);
	}

}
