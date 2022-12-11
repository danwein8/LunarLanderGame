import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LevelTester extends Applet implements Runnable, KeyListener 
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
	
	LunarLander lander = new LunarLander(50, 50, 20, 20, 30.0, 100.0, 20, 2);
	
	ArrayList<Ammo> ammo = new ArrayList<Ammo>();
	ArrayList<Fuel> fuel = new ArrayList<Fuel>();
	ArrayList<Health> health = new ArrayList<Health>();

	boolean noWin = true;
	
	Ceiling ceil = new Ceiling(0, 0);
	//ArrayList<StationaryEnemy> enemy = new ArrayList<StationaryEnemy>();
	//ArrayList<UpandDownEnemy> upandDn = new ArrayList<UpandDownEnemy>();
	//ArrayList<KamikazeEnemy> kamikaze = new ArrayList<KamikazeEnemy>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Boss boss = new Boss(2000 * ceil.difficulty, 250, 200, 300, null, 20 * ceil.difficulty, ceil.difficulty);
	
	Rect locator = new Rect(300, 300, 50, 50);
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
	
	public void init()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		enemies.add(new StationaryEnemy(600, 400, 20, 20, null, 1));
		enemies.add(new StationaryEnemy(1700, 400, 20, 20, null, 1));
		//upandDn.add(new UpandDownEnemy(500, 400, 20, 20, null, 1, -30));
		//upandDn.add(new UpandDownEnemy(1500, 400, 20, 20, null, 1));
		enemies.add(new KamikazeEnemy(1000, 200, 20, 40, null, 1));
		enemies.add(new KamikazeEnemy(1000, 500, 20, 40, null, 1));
		//enemies.add(new Boss(2000 * ceil.difficulty, 250, 200, 300, null, 20 * ceil.difficulty, ceil.difficulty));
		ammo.add(new Ammo(400, 400, 10, 10, 50, null));
		ammo.add(new Ammo(450, 300, 10, 10, 50, null));
		ammo.add(new Ammo(500, 200, 10, 10, 50, null));
		fuel.add(new Fuel(300, 300, 10, 10, 10, null));
		fuel.add(new Fuel(500, 400, 10, 10, 10, null));
		health.add(new Health(600, 300, 10, 10, 1, null));
		
		moveUp = new GameAction("moveUp");
		moveLeft = new GameAction("moveLeft");
    	moveRight = new GameAction("moveRight");
    	moveDown = new GameAction("moveDown");
    	fire = new GameAction("fire");
    	
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
		
		addKeyListener(this);
		
		t = new Thread(this);
		
		t.start();
	}

	@Override
	public void run() {
		while (noWin) {
			/**
			 * INPUT
			 */
			if (moveRight.isPressed()) locator.moveRight(5);
			if (moveLeft.isPressed()) locator.moveLeft(5);
			if (moveUp.isPressed()) locator.moveUp(5);
			if (moveDown.isPressed()) locator.moveDown(5);

			if (moveRight.isPressed()) Camera.moveRight(5);
			if (moveLeft.isPressed()) Camera.moveLeft(5);
			//if (moveUp.isPressed()) Camera.moveUp(5);
			//if (moveDown.isPressed()) Camera.moveDown(5);

			/**
			 * ENEMY UPDATE
			 */
			if (counter == target1) {
				for (int i = 0; i < enemies.size(); i++) enemies.get(i).update();
				for (int i = 0; i < boss.enemies.size(); i++) boss.enemies.get(i).update();
				for (int i = 0; i < boss.projectiles.size(); i++) boss.projectiles.get(i).launchProjectileL();
			}  
			if (counter == target2) {
				for (int i = 0; i < enemies.size(); i++)
					if (enemies.get(i) instanceof StationaryEnemy) enemies.get(i).update2();
				for (int i = 0; i < enemies.size(); i++) 
					if (enemies.get(i) instanceof KamikazeEnemy) enemies.get(i).update();
				for (int i = 0; i < boss.enemies.size(); i++) boss.enemies.get(i).update();
				for (int i = 0; i < boss.projectiles.size(); i++) boss.projectiles.get(i).launchProjectileL();
				counter = 0;
			}
			counter++;
			
			if (bossCounter == bossTarget) {
				boss.update();
				bossCounter = 0;
			}
			bossCounter++;
			
			/**
			 * ENEMY AND PROJECTILE COLLISION
			 */
			for (int i = 0; i < enemies.size(); i++) {
				if (locator.overlaps(enemies.get(i))) {
					lander.health--;
					enemies.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			for (int i = 0; i < boss.projectiles.size(); i++) {
				if (locator.overlaps(boss.projectiles.get(i))) {
					lander.health--;
					boss.projectiles.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			for (int i = 0; i < boss.enemies.size(); i++) {
				if (locator.overlaps(boss.enemies.get(i))) {
					lander.health--;
					boss.enemies.remove(i);
					if (lander.health == 0) noWin = false;
				}
			}
			
			
			/**
			 * WALL AND PLATFORM COLLISION
			 */
			for (int i = 0; i < ceil.ceiling.size(); i++) {
				if (locator.overlaps(ceil.ceiling.get(i))) {
					noWin = false;
				}
			}
			for (int i = 0; i < ceil.floor.size(); i++) {
				if (locator.overlaps(ceil.floor.get(i))) {
					noWin = false;
				}
			}
			if (locator.overlaps(ceil.endPlatform)) noWin = false;
			
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
				for (int j = 0; j < enemies.size(); j++) {
					if (lander.projectiles.get(i).overlaps(enemies.get(j))) enemies.remove(j);
				}
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

		g.setColor(Color.BLACK);
		ceil.draw(g);
		
		locator.draw(g);
		
		// enemies
		for (int i =  0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		boss.draw(g);
		for (int i = 0; i < boss.projectiles.size(); i++) {
			boss.projectiles.get(i).draw(g);
		}
		for (int i = 0; i < boss.enemies.size(); i++) {
			boss.enemies.get(i).draw(g);
		}
		
		g.drawString("(" + locator.x + ", " + locator.y + ")", 10+Camera.x, 20+Camera.y);
		
		if (locator.overlaps(ceil.endPlatform)) {
			g.setColor(Color.GREEN);
			g.setFont(font);
			g.drawString(win, 300 + Camera.x, 400 + Camera.y);
		}
		
		for (int i = 0; i < ceil.ceiling.size(); i++) {
			if (locator.overlaps(ceil.ceiling.get(i))) {
				g.setColor(Color.RED);
				g.setFont(font);
				g.drawString(lose, 500 + Camera.x, 400 + Camera.y);
			}
		}
		for (int i = 0; i < ceil.floor.size(); i++) {
			if (locator.overlaps(ceil.floor.get(i))) {
				g.setColor(Color.RED);
				g.setFont(font);
				g.drawString(lose, 500 + Camera.x, 400 + Camera.y);
			}
		}

		if (lander.health == 0) {
			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString(lose, 500 + Camera.x, 400 + Camera.y);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {	}
	
}
