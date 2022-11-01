import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GameTest1 extends Applet implements Runnable, KeyListener 
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
	
	// the smaller the lander is the harder the game gets
	LunarLander lander = new LunarLander(50, 50, 20, 20, 30.0, 100.0);
	
	Rect[] ground =
	{
		new Rect(0, 700, 50, 500),
		new Rect(50, 500, 50, 500),
		new Rect(100, 650, 50, 500),
		new Rect(150, 750, 50, 500),
		new Rect(200, 450, 50, 500),
		new Rect(250, 575, 50, 500),
		new Rect(300, 600, 50, 500),
		new Rect(350, 650, 50, 500),
		new Rect(400, 700, 50, 500),
		new Rect(450, 550, 50, 500),
		new Rect(500, 500, 50, 500),
		new Rect(550, 650, 50, 500),
		new Rect(600, 750, 50, 500),
		new Rect(650, 450, 50, 500),
		new Rect(700, 575, 50, 500),
		new Rect(750, 600, 50, 500),
		new Rect(800, 450, 50, 500),
		new Rect(850, 575, 50, 500),
		new Rect(900, 600, 50, 500),
		new Rect(950, 650, 50, 500),
		new Rect(1000, 700, 50, 500),
		new Rect(1050, 550, 50, 500),
		new Rect(1100, 500, 50, 500),
		new Rect(1150, 650, 50, 500),
		new Rect(1200, 750, 50, 500),
		new Rect(1250, 450, 50, 500),
		new Rect(1300, 575, 50, 500),
		new Rect(1350, 600, 50, 500),
	};
	
	Rect landingPlatform = new Rect(1400, 400, 40, 20);
	
	double gravity = 0.1;
	
	final Font fuelFont = new Font("Arial", Font.BOLD, 20);
	final Font font = new Font("Arial", Font.BOLD, 40);
	
	String lose = "YOU CRASHED AND DIED";
	String win = "YOU SUCCESSFULLY LANDED AND EVERYONE LIVED"; 
	
	
	public void init()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		
		offscreen_i = createImage((mode.getWidth() * 10), mode.getHeight());
		
		offscreen_g = offscreen_i.getGraphics();
				
		requestFocus();
		
		addKeyListener(this);
		
		t = new Thread(this);
		
		t.start();
	}

	@Override
	public void run() {
		lander.setAcceleration(0, gravity);
		while (noWin) {
			
			if (lander.ax > 0 || lander.ax < 0) lander.sideDecel(.9);
			if (lander.ay < 0.1) lander.gravityAccel(.06);
			
			if (up_pressed) {
				if (lander.ay > -0.3) lander.liftAccel(8 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.fuel > 0) lander.burnFuel();
			}
			if (rt_pressed) {
				if (lander.ax < 0.3) lander.moveRightAccel(3 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.fuel > 0) lander.burnFuel();
			}
			if (lt_pressed) {
				if (lander.ax > -0.3) lander.moveLeftAccel(3 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.fuel > 0) lander.burnFuel();
			}
			// function to move the camera to follow the lander
			if (lander.vx != 0) Camera.move((int)lander.vx);
			if (lander.x < 0) lander.x = 0;
			
			lander.moveBasedOnPhysics();
			
			for (int i = 0; i < ground.length; i++) {
				if (lander.overlaps(ground[i])) {
					noWin = false;
				}
			}
			
			if (lander.overlaps(landingPlatform)) noWin = false;
			
			repaint();
			try 
			{
				Thread.sleep(15);
			}
			catch (Exception x) { };
		}
	}
	
	public void update(Graphics g) {
		offscreen_g.clearRect(0, 0, (mode.getWidth() * 10), mode.getHeight());
		
		paint(offscreen_g);
		
		g.drawImage(offscreen_i, 0-Camera.x, 0-Camera.y, null);
	}
	
	public void paint(Graphics g) {
		
		lander.draw(g);
		
		for (int i = 0; i < ground.length; i++) {
			g.setColor(Color.black);
			ground[i].draw(g);
			g.fillRect((int)ground[i].x, (int)ground[i].y, ground[i].w, ground[i].h);
		}
		g.setColor(Color.green);
		g.fillRect((int)landingPlatform.x, (int)landingPlatform.y, landingPlatform.w, landingPlatform.h);
		
		g.setColor(Color.blue);
		g.setFont(fuelFont);
		g.drawString("FUEL" + String.valueOf(String.format("%.2f", lander.getFuel())), 10+Camera.x, 20+Camera.y);
		g.drawString("LANDER VX: " + String.format("%.2f", lander.vx) + "LANDER VY: " + String.format("%.2f", lander.vy), 10+Camera.x, 40+Camera.y);
		g.drawString("LANDER AX: " + String.format("%.2f", lander.ax) + "LANDER AY: " + String.format("%.2f", lander.ay), 10+Camera.x, 60+Camera.y);
		g.drawString("LANDER X: " + String.format("%.2f", lander.x) + "LANDER Y: " + String.format("%.2f", lander.y), 10+Camera.x, 80+Camera.y);
		
		if (lander.overlaps(landingPlatform)) {
			g.setColor(Color.green);
			g.setFont(font);
			g.drawString(win, 300+Camera.x, 400+Camera.y);
		}
		
		for (int i = 0; i < ground.length; i++) {
			if (lander.overlaps(ground[i])) {
				g.setColor(Color.red);
				g.setFont(font);
				g.drawString(lose, 500+Camera.x, 400+Camera.y);
			}
		}
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
