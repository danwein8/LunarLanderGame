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
	
	Rect locator = new Rect(300, 300, 50, 50);
	
	public void init()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		//level.add(new LevelRect(0, 0, 0, 0));
		
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
		/*for (int i = 0; i < level.size(); i++) {
			level.get(i).draw(g);
		}
		level.draw(g);*/
		ceil.draw(g);
		
		locator.draw(g);
		
		g.setColor(Color.BLACK);
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
