import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public abstract class GameBase extends Applet implements Runnable, KeyListener
{
	Thread t;
	
	Image    offscreen_Image;
	Graphics offscreen_g;
	
	boolean[] pressing = new boolean[1024];
	
	public static final int _UP         = KeyEvent.VK_UP;
	public static final int _DN         = KeyEvent.VK_DOWN;
	public static final int _LT         = KeyEvent.VK_LEFT;
	public static final int _RT         = KeyEvent.VK_RIGHT;
	
	public static final int _A          = KeyEvent.VK_A;
	public static final int _B          = KeyEvent.VK_B;
	public static final int _C          = KeyEvent.VK_C;
	public static final int _D          = KeyEvent.VK_D;
	public static final int _E          = KeyEvent.VK_E;
	public static final int _F          = KeyEvent.VK_F;
	public static final int _G          = KeyEvent.VK_G;
	public static final int _H          = KeyEvent.VK_H;
	public static final int _I          = KeyEvent.VK_I;
	public static final int _J          = KeyEvent.VK_J;
	public static final int _K          = KeyEvent.VK_K;
	public static final int _L          = KeyEvent.VK_L;
	public static final int _M          = KeyEvent.VK_M;
	public static final int _N          = KeyEvent.VK_N;
	public static final int _O          = KeyEvent.VK_O;
	public static final int _P          = KeyEvent.VK_P;
	public static final int _Q          = KeyEvent.VK_Q;
	public static final int _R          = KeyEvent.VK_R;
	public static final int _S          = KeyEvent.VK_S;
	public static final int _T          = KeyEvent.VK_T;
	public static final int _U          = KeyEvent.VK_U;
	public static final int _V          = KeyEvent.VK_V;
	public static final int _W          = KeyEvent.VK_W;
	public static final int _X          = KeyEvent.VK_X;
	public static final int _Y          = KeyEvent.VK_Y;
	public static final int _Z          = KeyEvent.VK_Z;

	public static final int _1          = KeyEvent.VK_1;
	public static final int _2          = KeyEvent.VK_2;
	public static final int _3          = KeyEvent.VK_3;
	public static final int _4          = KeyEvent.VK_4;
	public static final int _5          = KeyEvent.VK_5;
	public static final int _6          = KeyEvent.VK_6;
	public static final int _7          = KeyEvent.VK_7;
	public static final int _8          = KeyEvent.VK_8;
	public static final int _9          = KeyEvent.VK_9;
	
	public static final int CTRL        = KeyEvent.VK_CONTROL;
	public static final int SHFT        = KeyEvent.VK_SHIFT;
	public static final int ALT         = KeyEvent.VK_ALT;
	
	public static final int SPACE       = KeyEvent.VK_SPACE;
	
	public static final int COMMA       = KeyEvent.VK_COMMA;
	public static final int PERIOD      = KeyEvent.VK_PERIOD;
	public static final int SLASH       = KeyEvent.VK_SLASH;
	public static final int SEMICOLON   = KeyEvent.VK_SEMICOLON;
	public static final int COLON       = KeyEvent.VK_COLON;
	public static final int QUOTE       = KeyEvent.VK_QUOTE;
	
	public static final int F1          = KeyEvent.VK_F1;
	public static final int F2          = KeyEvent.VK_F2;
	public static final int F3          = KeyEvent.VK_F3;
	public static final int F4          = KeyEvent.VK_F4;
	public static final int F5          = KeyEvent.VK_F5;
	public static final int F6          = KeyEvent.VK_F6;
	public static final int F7          = KeyEvent.VK_F7;
	public static final int F8          = KeyEvent.VK_F8;
	public static final int F9          = KeyEvent.VK_F9;
	public static final int F10         = KeyEvent.VK_F10;
	public static final int F11         = KeyEvent.VK_F11;
	public static final int F12         = KeyEvent.VK_F12;
	
	
	public void init()
	{		
		offscreen_Image = createImage(1600, 1200);
		offscreen_g     = offscreen_Image.getGraphics();

		requestFocus();
		
		addKeyListener(this);
		
		initialize();
		
		t = new Thread(this);
		
		t.start();
	}
	
	public void initialize() {   }

	// abstract function that holds the place for your entire game loop
	public abstract void inGameLoop();
	
	public void run()
	{

		while(true)
		{
			
			inGameLoop();
			
				
			repaint();
			
   		try
   		{
   			t.sleep(15);
   		}
   		catch(Exception x) {};
		}
	}
	
	public void update(Graphics g)
	{
		offscreen_g.clearRect(0, 0, 1600, 1200);
		
		paint(offscreen_g);
		
		g.drawImage(offscreen_Image, 0, 0, null);
	}
	
	public void keyPressed(KeyEvent e)
	{
		pressing[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e)
	{
		pressing[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e)  {	}
}