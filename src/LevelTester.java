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
	
	boolean up_pressed = false;
	boolean dn_pressed = false;
	boolean lt_pressed = false;
	boolean rt_pressed = false;
	
	boolean noWin = true;
	
	//Level level = new Level(0, 0);
	//LevelRect level = new LevelRect(0, 0, 0, 0);
	//ArrayList<LevelRect> level = new ArrayList<LevelRect>();
	Ceiling ceil = new Ceiling(0, 0);
	ArrayList<StationaryEnemy> enemy = new ArrayList<StationaryEnemy>();
	//ArrayList<UpandDownEnemy> upandDn = new ArrayList<UpandDownEnemy>();
	ArrayList<KamikazeEnemy> kamikaze = new ArrayList<KamikazeEnemy>();
	Boss boss = new Boss(2000, 250, 200, 200, null, 20);
	
	Rect locator = new Rect(300, 300, 50, 50);
	// slows down update speeds
	int counter = 0;
	int target1 = 15;
	int target2 = 30;
	// slows down boss attack speed
	int bossCounter = 0;
	int bossTarget = 60;
	
	public void init()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		//level.add(new LevelRect(0, 0, 0, 0));
		enemy.add(new StationaryEnemy(600, 400, 20, 20, null, 1));
		enemy.add(new StationaryEnemy(1700, 400, 20, 20, null, 1));
		//upandDn.add(new UpandDownEnemy(500, 400, 20, 20, null, 1, -30));
		//upandDn.add(new UpandDownEnemy(1500, 400, 20, 20, null, 1));
		kamikaze.add(new KamikazeEnemy(1000, 200, 20, 40, null, 1));
		kamikaze.add(new KamikazeEnemy(1000, 500, 20, 40, null, 1));
		
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
			if (rt_pressed) locator.moveRight(5);
			if (lt_pressed) locator.moveLeft(5);
			if (up_pressed) locator.moveUp(5);
			if (dn_pressed) locator.moveDown(5);

			if (rt_pressed) Camera.moveRight(5);
			if (lt_pressed) Camera.moveLeft(5);
			if (up_pressed) Camera.moveUp(5);
			if (dn_pressed) Camera.moveDown(5);

			// enemies
			if (counter == target1) {
				for (int i = 0; i < enemy.size(); i++) {
					enemy.get(i).update();
				}
				for (int i = 0; i < kamikaze.size(); i++) {
					kamikaze.get(i).update();
				}
				for (int i = 0; i < boss.enemies.size(); i++) {
					boss.enemies.get(i).update();
				}
				for (int i = 0; i < boss.projectiles.size(); i++) {
					boss.projectiles.get(i).launchProjectileL();
				}
			}  
			if (counter == target2) {
				for (int i = 0; i < enemy.size(); i++) {
					enemy.get(i).update2();
				}
				for (int i = 0; i < kamikaze.size(); i++) {
					kamikaze.get(i).update();
				}
				for (int i = 0; i < boss.enemies.size(); i++) {
					boss.enemies.get(i).update();
				}
				for (int i = 0; i < boss.projectiles.size(); i++) {
					boss.projectiles.get(i).launchProjectileL();
				}
				counter = 0;
			}
			counter++;
			
			if (bossCounter == bossTarget) {
				boss.update();
				bossCounter = 0;
			}
			bossCounter++;
			


			//if (level.get(0).collision(locator)) noWin = true;

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
		
		// stationary enemies
		for (int i =  0; i < enemy.size(); i++) {
			enemy.get(i).draw(g);
		}
		
		//kamikaze enemies
		for (int i = 0; i < kamikaze.size(); i++) {
			kamikaze.get(i).draw(g);
		}
		
		boss.draw(g);
		for (int i = 0; i < boss.projectiles.size(); i++) {
			boss.projectiles.get(i).draw(g);
		}
		for (int i = 0; i < boss.enemies.size(); i++) {
			boss.enemies.get(i).draw(g);
		}
		
		g.drawString("(" + locator.x + ", " + locator.y + ")", 10+Camera.x, 20+Camera.y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) 	 up_pressed = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)  dn_pressed = true;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)  lt_pressed = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) rt_pressed = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)     up_pressed = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)   dn_pressed = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)   lt_pressed = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)  rt_pressed = false;	
	}

	@Override
	public void keyTyped(KeyEvent e) {	}
	
}
