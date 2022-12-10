import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * the Boss enemy is going to be a huge enemy that can take multiple shots that blocks the
 * end of the level so the player can't win until the player beats him, he will also shoot
 * some projectiles and some kamikaze enemies
 * @author danielweiner
 *
 */
public class Boss extends Enemy {
	
	ArrayList<Projectiles> projectiles = new ArrayList<Projectiles>();
	ArrayList<KamikazeEnemy> enemies = new ArrayList<KamikazeEnemy>();

	public Boss(double x, double y, int w, int h, LunarLander target, int health) {
		super(x, y, w, h, target, health);
	}

	@Override
	public void update() {
		if (this.getAgeInSeconds() > 10) {
			launchKamikaze();
			shoot();
		}
	}

	public void shoot() {
		Random rand = new Random();
		projectiles.add(new Projectiles(this.x + (this.w / 2), rand.nextInt(600) + 200, 5, 5));
	}
	
	public ArrayList<Projectiles> getProjectiles() {
		return projectiles;
	}
	
	public void removeProjectile(int index) {
		projectiles.remove(index);
	}
	
	public void launchKamikaze() {
		Random rand = new Random();
		enemies.add(new KamikazeEnemy(this.x, rand.nextInt(600) + 200, 20, 40, null, 1));
	}
	
	public void draw(Graphics g) {
		g.drawRect((int)x, (int)y, w, h);
	}
	
}
