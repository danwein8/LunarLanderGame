import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * MAIN TESTER FOR THE SPACE MOVEMENT
 * @author danielweiner
 *
 */
public class GameTest3 extends Applet implements Runnable 
{
	
	private static final long serialVersionUID = 1L;
	
	private GraphicsDevice device;
	private DisplayMode mode;

	Thread t;
	
	Image 	 offscreen_i;
	Graphics offscreen_g;
	
	GameAction moveUp;
	GameAction moveLeft;
	GameAction moveRight;
	GameAction moveDown;
	GameAction fire;
	InputManager inputManager = new InputManager(this);
	
	double gravity = 0.1;
	
	//LunarLander lander = new LunarLander(50, 50, 20, 20, 30.0, 100.0, 20, 2);
	LunarLander lander;
	UI ui;
	
	ArrayList<Ammo> ammo = new ArrayList<Ammo>();
	ArrayList<Fuel> fuel = new ArrayList<Fuel>();
	ArrayList<Health> health = new ArrayList<Health>();

	boolean noWin = true;
	
	Ceiling ceil = new Ceiling(0, 0);
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Boss> bosses = new ArrayList<Boss>();
	Boss boss = new Boss(2000 * ceil.difficulty, 250, 20, 128, null, 20 * ceil.difficulty, ceil.difficulty);
	Boss boss2 = new Boss(2000 * ceil.difficulty, 500, 20, 128, null, 20 * ceil.difficulty, ceil.difficulty);
	
	// slows down update speeds
	int counter = 0;
	int target1 = 15;
	int target2 = 30;
	// slows down boss attack speed
	int bossCounter = 0;
	int bossTarget = 60;
	
	final Font itemFont = new Font("Arial", Font.BOLD, 12);
	final Font fuelFont = new Font("Arial", Font.BOLD, 20);
	final Font font = new Font("Arial", Font.BOLD, 40);
	
	String lose = "YOU CRASHED AND DIED";
	String win = "YOU SUCCESSFULLY LANDED AND EVERYONE LIVED"; 
	
	Image[] landerArr;
	Image[] ignitionArr;
	Image[] projectileArr;
	Image[] enemyProjectileArr;
	Image[] ammoPickupArr;
	Image[] fuelPickupArr;
	Image[] healthPickupArr;
	Image[] explosionArr;
	Image[] bigBossArr;
	Image[] smallBossArr;
	Image[] stationaryEnemyArr;
	Image[] kamikazeEnemyArr;
	Image[] platformArr;
	Image[] heartArr;
	Image[] emptyHeartArr;
	
	AnimationBk projectileAnim = new AnimationBk();
	AnimationBk enemyProjectileAnim = new AnimationBk();
	
	public void init()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		enemies.add(new StationaryEnemy(600, 400, 20, 20, null, 1));
		enemies.add(new StationaryEnemy(1700, 400, 20, 20, null, 1));
		enemies.add(new KamikazeEnemy(1000, 200, 20, 40, null, 1));
		enemies.add(new KamikazeEnemy(1000, 500, 20, 40, null, 1));
		bosses.add(boss);
		bosses.add(boss2);
		//enemies.add(new Boss(2000 * ceil.difficulty, 250, 200, 300, null, 20 * ceil.difficulty, ceil.difficulty));
		ammo.add(new Ammo(400, 400, 10, 10, 50));
		ammo.add(new Ammo(450, 300, 10, 10, 50));
		ammo.add(new Ammo(500, 200, 10, 10, 50));
		fuel.add(new Fuel(300, 300, 10, 10, 10));
		fuel.add(new Fuel(500, 400, 10, 10, 10));
		health.add(new Health(600, 300, 10, 10, 1));
		
		lander = new LunarLander(50, 200, 20, 20, 30.0, 100.0, 20, 2);
		createSprites();
		ui = new UI(60, 60, lander);
		
		moveUp = new GameAction("moveUp");
		moveLeft = new GameAction("moveLeft");
    	moveRight = new GameAction("moveRight");
    	moveDown = new GameAction("moveDown");
    	fire = new GameAction("fire", GameAction.DETECT_INITAL_PRESS_ONLY);
    	
    	// ARROW KEYS
    	inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
    	inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    	inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
    	inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
    	// WASD KEYS
    	inputManager.mapToKey(moveUp, KeyEvent.VK_W);
    	inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
    	inputManager.mapToKey(moveRight, KeyEvent.VK_D);
    	inputManager.mapToKey(moveDown, KeyEvent.VK_S);
    	inputManager.mapToKey(fire, KeyEvent.VK_SPACE);
		
		offscreen_i = createImage((mode.getWidth() * 15), mode.getHeight());
		
		offscreen_g = offscreen_i.getGraphics();
				
		requestFocus();
		
		addKeyListener(inputManager);
		
		t = new Thread(this);
		
		t.start();
	}

	@Override
	public void run() {
		lander.setAcceleration(0, gravity);
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		while (noWin) {

			long elapsedTime = System.currentTimeMillis() - currTime;
			currTime += elapsedTime;
			lander.landerImage.update(elapsedTime);
			lander.flame.update(elapsedTime);
			boss.enemySprite.update(elapsedTime);
			Ceiling.end.update(elapsedTime);
			UIFull.heart.update(elapsedTime);
			UIEmpty.emptyHeart.update(elapsedTime);
			//for (int i = 0; i < lander.projectiles.size(); i++) lander.projectiles.get(i).projectileImage.update(elapsedTime);
			//for (int i = 0; i < boss.enemies.size(); i++) boss.enemies.get(i).enemySprite.update(elapsedTime);
			//for (int i = 0; i < boss.projectiles.size(); i++) boss.projectiles.get(i).enemyProjectileImage.update(elapsedTime);
			//for (int i = 0; i < enemies.size(); i++) enemies.get(i).enemySprite.update(elapsedTime);
			//for (int i = 0; i < ammo.size(); i++) ammo.get(i).sprite.update(elapsedTime);
			//for (int i = 0; i < fuel.size(); i++) fuel.get(i).sprite.update(elapsedTime);
			//for (int i = 0; i < health.size(); i++) health.get(i).sprite.update(elapsedTime);
			
			
			/**
			 * INPUT
			 */
			if (lander.ax > 0 || lander.ax < 0) lander.sideDecel(.9);
			if (lander.ay < 0.1) lander.gravityAccel(.06);
			
			if (moveUp.isPressed()) {
				if (lander.ay > -0.3) lander.liftAccel(8 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.getFuel() > 0) lander.burnFuel();
			}
			if (moveRight.isPressed()) {
				if (lander.ax > -0.3) lander.moveRightAccel(3 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.getFuel() > 0) lander.burnFuel();
			}
			if (moveLeft.isPressed()) {
				if (lander.ax > -0.3) lander.moveLeftAccel(3 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.getFuel() > 0) lander.burnFuel();
			}
			if (fire.isPressed() && lander.getAmmo() > 0) lander.shoot();
			// function to move the camera to follow the lander
			if (lander.vx != 0) Camera.move((int)lander.vx);
			if (lander.x < 0) lander.x = 0;
			
			lander.moveBasedOnPhysics();

			/*
			 if (moveRight.isPressed()) lander.moveRight(5);
			 if (moveLeft.isPressed()) lander.moveLeft(5);
			 if (moveUp.isPressed()) lander.moveUp(5);
			 if (moveDown.isPressed()) lander.moveDown(5);
			 if (fire.isPressed()) lander.shoot();

			 if (moveRight.isPressed()) Camera.moveRight(5);
			 if (moveLeft.isPressed()) Camera.moveLeft(5);
			 //if (moveUp.isPressed()) Camera.moveUp(5);
			 //if (moveDown.isPressed()) Camera.moveDown(5);
			 
			 for (int i = 0; i < lander.getProjectiles().size(); i++)
				 lander.getProjectiles().get(i).launchProjectile();
			 */

			/**
			 * ENEMY UPDATE
			 */
			if (counter == target1) {
				for (int i = 0; i < enemies.size(); i++) enemies.get(i).update();
				for (int i = 0; i < boss.enemies.size(); i++) boss.enemies.get(i).update();
				for (int i = 0; i < boss.projectiles.size(); i++) boss.projectiles.get(i).launchProjectileL();
				for (int i = 0; i < boss2.enemies.size(); i++) boss2.enemies.get(i).update();
				for (int i = 0; i < boss2.projectiles.size(); i++) boss2.projectiles.get(i).launchProjectileL();
			}  
			if (counter == target2) {
				for (int i = 0; i < enemies.size(); i++)
					if (enemies.get(i) instanceof StationaryEnemy) enemies.get(i).update2();
				for (int i = 0; i < enemies.size(); i++) 
					if (enemies.get(i) instanceof KamikazeEnemy) enemies.get(i).update();
				for (int i = 0; i < boss.enemies.size(); i++) boss.enemies.get(i).update();
				for (int i = 0; i < boss.projectiles.size(); i++) boss.projectiles.get(i).launchProjectileL();
				for (int i = 0; i < boss2.enemies.size(); i++) boss2.enemies.get(i).update();
				for (int i = 0; i < boss2.projectiles.size(); i++) boss2.projectiles.get(i).launchProjectileL();
				counter = 0;
			}
			counter++;
			
			if (bossCounter == bossTarget) {
				for (int i = 0; i < bosses.size(); i++)
					bosses.get(i).update();
				bossCounter = 0;
			}
			bossCounter++;
			
			/**
			 * ENEMY AND PROJECTILE COLLISION
			 */
			for (int i = 0; i < enemies.size(); i++) {
				if (lander.overlaps(enemies.get(i))) {
					lander.health--;
					enemies.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			for (int i = 0; i < boss.projectiles.size(); i++) {
				if (lander.overlaps(boss.projectiles.get(i))) {
					lander.health--;
					boss.projectiles.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			for (int i = 0; i < boss.enemies.size(); i++) {
				if (lander.overlaps(boss.enemies.get(i))) {
					lander.health--;
					boss.enemies.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			for (int i = 0; i < boss2.projectiles.size(); i++) {
				if (lander.overlaps(boss2.projectiles.get(i))) {
					lander.health--;
					boss2.projectiles.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			for (int i = 0; i < boss2.enemies.size(); i++) {
				if (lander.overlaps(boss2.enemies.get(i))) {
					lander.health--;
					boss2.enemies.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			
			
			/**
			 * WALL AND PLATFORM COLLISION
			 */
			for (int i = 0; i < ceil.ceiling.size(); i++) {
				if (lander.overlaps(ceil.ceiling.get(i))) {
					noWin = false;
				}
			}
			for (int i = 0; i < ceil.floor.size(); i++) {
				if (lander.overlaps(ceil.floor.get(i))) {
					noWin = false;
				}
			}
			if (lander.overlaps(ceil.endPlatform)) noWin = false;
			
			/**
			 * COLLECTABLE COLLISION
			 */
			for (int i = 0; i < ammo.size(); i++) {
				if (lander.overlaps(ammo.get(i))) {
					ammo.get(i).pickUp(lander);
					ammo.remove(i);
				}
			}
			for (int i = 0; i < fuel.size(); i++) {
				if (lander.overlaps(fuel.get(i))) {
					fuel.get(i).pickUp(lander);
					fuel.remove(i);
				}
			}
			for (int i = 0; i < health.size(); i++) {
				if (lander.overlaps(health.get(i))) {
					health.get(i).pickUp(lander);
					health.remove(i);
				}
			}
			
			/**
			 * ENEMY COLLIDE WITH PLAYER PROJECTILES DETECTION
			 */
			for (int i = 0; i < lander.projectiles.size(); i++) {
				for (int j = 0; j < enemies.size(); j++)
					if (lander.projectiles.get(i).overlaps(enemies.get(j))) enemies.remove(j);
				// BOSS
				for (int j = 0; j < bosses.size(); j++) {
					if (lander.projectiles.get(i).overlaps(bosses.get(j))) {
						bosses.get(j).health--;
						if (bosses.get(j).health < 1) bosses.remove(j);
					}
				}
				// BOSSES ENEMIES HAVE TO BE ACCESSED THIS WAY B/C IF YOU ACCESS THEM THRU THE BOSSES ARRAYLIST
				// AFTER YOU KILL THE BOSS, ITS MINIONS BECOME INVINCIBLE
				for (int j = 0; j < boss.enemies.size(); j++)
					if (lander.projectiles.get(i).overlaps(boss.enemies.get(j))) boss.enemies.remove(j);
				for (int j = 0; j < boss2.enemies.size(); j++)
					if (lander.projectiles.get(i).overlaps(boss2.enemies.get(j))) boss2.enemies.remove(j);
			}
			
					

			repaint();
			try 
			{
				Thread.sleep(15);
			}
			catch (Exception x) { };
		}
	}
	
	public void update(Graphics g) {
		offscreen_g.clearRect(0, 0, (mode.getWidth() * 15), mode.getHeight());
		
		paint(offscreen_g);
		
		g.drawImage(offscreen_i, 0-Camera.x, 0-Camera.y, null);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.decode("#FF00FF"));
		g.fillRect(0, 0, 20000, 3000);

		g.setColor(Color.BLACK);
		ceil.draw(g);
		
		// LANDER
		lander.draw(g);

		for (int i = 0; i < lander.getProjectiles().size(); i++)
			lander.getProjectiles().get(i).draw(g);
		
		// PICKUPS
		for (int i = 0; i < ammo.size(); i++) ammo.get(i).draw(g);
		for (int i = 0; i < fuel.size(); i++) fuel.get(i).draw(g);
		for (int i = 0; i < health.size(); i++) health.get(i).draw(g);
		
		// enemies
		for (int i =  0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		for (int i = 0; i < bosses.size(); i++)
			bosses.get(i).draw(g);
		for (int i = 0; i < boss.projectiles.size(); i++) {
			boss.projectiles.get(i).draw(g);
  		}
		for (int i = 0; i < boss.enemies.size(); i++) {
			boss.enemies.get(i).draw(g);
		}
		for (int i = 0; i < boss2.projectiles.size(); i++) {
			boss2.projectiles.get(i).draw(g);
  		}
		for (int i = 0; i < boss2.enemies.size(); i++) {
			boss2.enemies.get(i).draw(g);
		}
		
		g.drawString("(" + lander.x + ", " + lander.y + ")", 10+Camera.x, 20+Camera.y);
		g.drawString("HEALTH: " + lander.health + "		FUEL: " + lander.fuel + "		AMMO: " + lander.bullets, 10+Camera.x, 40+Camera.y);
		//ui.draw(g);
		
		if (lander.overlaps(ceil.endPlatform)) {
			g.setColor(Color.GREEN);
			g.setFont(font);
			g.drawString(win, 300 + Camera.x, 400 + Camera.y);
		}
		
		for (int i = 0; i < ceil.ceiling.size(); i++) {
			if (lander.overlaps(ceil.ceiling.get(i))) {
				g.setColor(Color.WHITE);
				g.setFont(font);
				g.drawString(lose, 500 + Camera.x, 400 + Camera.y);
			}
		}
		for (int i = 0; i < ceil.floor.size(); i++) {
			if (lander.overlaps(ceil.floor.get(i))) {
				g.setColor(Color.WHITE);
				g.setFont(font);
				g.drawString(lose, 500 + Camera.x, 400 + Camera.y);
			}
		}

		if (lander.health == 0) {
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawString(lose, 500 + Camera.x, 400 + Camera.y);
		}
	}
	
	public Image loadImage(String filename) {
		return Toolkit.getDefaultToolkit().getImage(filename);
	}
	
	public void createSprites() {
		landerArr = new Image[16];
		ignitionArr = new Image[3];
		projectileArr = new Image[4];
		enemyProjectileArr = new Image[4];
		ammoPickupArr = new Image[4];
		fuelPickupArr = new Image[8];
		healthPickupArr = new Image[6];
		explosionArr = new Image[4];
		bigBossArr = new Image[8];
		smallBossArr = new Image[3];
		stationaryEnemyArr = new Image[3];
		kamikazeEnemyArr = new Image[3];
		platformArr = new Image[4];
		heartArr = new Image[1];
		emptyHeartArr = new Image[1];
		
		for (int i = 1; i < 17; i++)
			landerArr[i-1] = loadImage("Lander" + i + ".png");
		for (int i = 1; i < 4; i++)
			ignitionArr[i-1] = loadImage("ignition" + i + ".png");
		for (int i = 1; i < 5; i++)
			projectileArr[i-1] = loadImage("LanderProjectile" + i + ".png");
		for (int i = 1; i < 5; i++)
			enemyProjectileArr[i-1] = loadImage("EnemyProjectile" + i + ".png");
		for (int i = 1; i < 5; i++)
			ammoPickupArr[i-1] = loadImage("AmmoPickup" + i + ".png");
		for (int i = 1; i < 9; i++)
			fuelPickupArr[i-1] = loadImage("FuelPickup" + i + ".png");
		for (int i = 1; i < 7; i++)
			healthPickupArr[i-1] = loadImage("HealthPickup" + i + ".png");
		for (int i = 1; i < 5; i++)
			explosionArr[i-1] = loadImage("Explosion" + i + ".png");
		for (int i = 1; i < 9; i++)
			bigBossArr[i-1] = loadImage("BigBoss" + i + ".png");
		for (int i = 1; i < 4; i++)
			smallBossArr[i-1] = loadImage("SmallBoss" + i + ".png");
		for (int i = 1; i < 4; i++)
			stationaryEnemyArr[i-1] = loadImage("StaionaryShip" + i + ".png");
		for (int i = 1; i < 4; i++)
			kamikazeEnemyArr[i-1] = loadImage("MovingShip" + i + ".png");
		for (int i = 1; i < 4; i++)
			platformArr[i-1] = loadImage("Platform" + i + ".png");
		for (int i = 1; i < 2; i++)
			heartArr[i-1] = loadImage("FullHeart.png");
		for (int i = 1; i < 2; i++)
			emptyHeartArr[i-1] = loadImage("EmptyHeart.png");
		
		AnimationBk landerAnim = new AnimationBk();
		AnimationBk ignitionAnim = new AnimationBk();
		projectileAnim = new AnimationBk();
		enemyProjectileAnim = new AnimationBk();
		AnimationBk ammoPickupAnim = new AnimationBk();
		AnimationBk fuelPickupAnim = new AnimationBk();
		AnimationBk healthPickupAnim = new AnimationBk();
		AnimationBk explosionAnim = new AnimationBk();
		AnimationBk bigBossAnim = new AnimationBk();
		AnimationBk smallBossAnim = new AnimationBk();
		AnimationBk stationaryEnemyAnim = new AnimationBk();
		AnimationBk kamikazeEnemyAnim = new AnimationBk();
		AnimationBk platformAnim = new AnimationBk();
		AnimationBk heartAnim = new AnimationBk();
		AnimationBk emptyHeartAnim = new AnimationBk();
		
		for (int i = 0; i < landerArr.length; i++)
			landerAnim.addFrame(landerArr[i], 100);
		for (int i = 0; i < ignitionArr.length; i++)
			ignitionAnim.addFrame(ignitionArr[i], 100);
		for (int i = 0; i < projectileArr.length; i++)
			projectileAnim.addFrame(projectileArr[i], 100);
		for (int i = 0; i < enemyProjectileArr.length; i++)
			enemyProjectileAnim.addFrame(enemyProjectileArr[i], 100);
		for (int i = 0; i < ammoPickupArr.length; i++)
			ammoPickupAnim.addFrame(ammoPickupArr[i], 100);
		for (int i = 0; i < fuelPickupArr.length; i++)
			fuelPickupAnim.addFrame(fuelPickupArr[i], 100);
		for (int i = 0; i < healthPickupArr.length; i++)
			healthPickupAnim.addFrame(healthPickupArr[i], 100);
		for (int i = 0; i < explosionArr.length; i++)
			explosionAnim.addFrame(explosionArr[i], 100);
		for (int i = 0; i < bigBossArr.length; i++)
			bigBossAnim.addFrame(bigBossArr[i], 100);
		for (int i = 0; i < smallBossArr.length; i++)
			smallBossAnim.addFrame(smallBossArr[i], 100);
		for (int i = 0; i < stationaryEnemyArr.length; i++)
			stationaryEnemyAnim.addFrame(stationaryEnemyArr[i], 100);
		for (int i = 0; i < kamikazeEnemyArr.length; i++)
			kamikazeEnemyAnim.addFrame(kamikazeEnemyArr[i], 100);
		for (int i = 0; i < platformArr.length; i++)
			platformAnim.addFrame(platformArr[i], 100);
		for (int i = 0; i < heartArr.length; i++)
			heartAnim.addFrame(heartArr[i], 10000);
		for (int i = 0; i < emptyHeartArr.length; i++)
			emptyHeartAnim.addFrame(emptyHeartArr[i], 10000);
		
		lander.landerImage = landerAnim;
		lander.flame = ignitionAnim;
		lander.crash = explosionAnim;
		
		Projectiles.projectileImage = projectileAnim;
		
		boss.enemySprite = bigBossAnim;
		boss.crash = explosionAnim;
		boss.flame = ignitionAnim;
		boss2.enemySprite = bigBossAnim;
		boss2.crash = explosionAnim;
		boss2.flame = ignitionAnim;
		
		KamikazeEnemy.kamikazeSprite = kamikazeEnemyAnim;
		StationaryEnemy.stationarySprite = stationaryEnemyAnim;
		EnemyProjectiles.enemyProjectileImage = enemyProjectileAnim;
		Ammo.ammoSprite = ammoPickupAnim;
		Fuel.fuelSprite = fuelPickupAnim;
		Health.healthSprite = healthPickupAnim;
		
		Ceiling.end = platformAnim;
		UIFull.heart = heartAnim;
		UIEmpty.emptyHeart = emptyHeartAnim;
	}
	
}
