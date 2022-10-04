import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GameTest1 extends Applet implements Runnable, KeyListener {
	
	Thread t;
	
	Image 	 offscreen_i;
	Graphics offscreen_g;
	
	boolean up_pressed = false;
	boolean dn_pressed = false;
	boolean lt_pressed = false;
	boolean rt_pressed = false;
	
	LunarLander lander = new LunarLander(50, 50, 40, 40, 100.0, 20.0);
	
	Rect[] ground =
	{
		new Rect(0, 700, 100, 500),
		new Rect(100, 500, 100, 500),
		new Rect(200, 650, 100, 500),
		new Rect(300, 750, 100, 500),
		new Rect(400, 450, 100, 500),
		new Rect(500, 575, 100, 500),
		new Rect(600, 600, 100, 500),
		new Rect(700, 650, 100, 500),
		new Rect(800, 700, 100, 500),
		new Rect(900, 550, 100, 500),
		new Rect(1000, 500, 100, 500),
		new Rect(1100, 650, 100, 500),
		new Rect(1200, 750, 100, 500),
		new Rect(1300, 450, 100, 500),
		new Rect(1400, 575, 100, 500),
		new Rect(1500, 600, 100, 500),
	};
	
	double gravity = 0.5;
	
	
	public void init()
	{
		offscreen_i = createImage(1600, 1200);
		
		offscreen_g = offscreen_i.getGraphics();
		
		requestFocus();
		
		addKeyListener(this);
		
		t = new Thread(this);
		
		t.start();
	}

	@Override
	public void run() {
		lander.setAcceleration(0, gravity);
		while (true) {
			
			repaint();
			try 
			{
				t.sleep(15);
			}
			catch (Exception x) { };
		}
	}
	
	public void update(Graphics g) {
		offscreen_g.clearRect(0, 0, 1600, 1200);
		
		paint(offscreen_g);
		
		g.drawImage(offscreen_i, 0, 0, null);
	}
	
	public void paint(Graphics g) {
		lander.draw(g);
		
		for (int i = 0; i < ground.length; i++) {
			ground[i].draw(g);
			g.fillRect((int)ground[i].x, (int)ground[i].y, ground[i].w, ground[i].h);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
