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
	
	ArrayList<EnemyProjectiles> projectiles = new ArrayList<EnemyProjectiles>();
	ArrayList<KamikazeEnemy> enemies = new ArrayList<KamikazeEnemy>();
	int difficultyTimerScalar;

	public Boss(double x, double y, int w, int h, LunarLander target, int health, int difficulty) {
		super(x, y, w, h, target, health);
		difficultyTimerScalar = difficulty;
	}

	@Override
	public void update() {
		if (this.getAgeInSeconds() > (10 * difficultyTimerScalar)) {
			launchKamikaze();
			shoot();
		}
	}
	
	public void update2() {
		
	}

	public void shoot() {
		Random rand = new Random();
		projectiles.add(new EnemyProjectiles(this.x + (this.w / 2), rand.nextInt(600) + 200, 5, 5));
	}
	
	public ArrayList<EnemyProjectiles> getProjectiles() {
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
		g.drawImage(enemySprite.getImage(), (int)x, (int)y, null);
	}
	
}
