import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Random;

public class LevelRect extends Rect {
	
	private GraphicsDevice device;
	private DisplayMode mode;
	
	/**
	 * the location of the current grid on the x axis
	 */
	int x;
	/**
	 * the location of the current grid on the y axis
	 */
	int y;
	
	/**
	 * a scalar value used in the procedural generation
	 */
	int difficulty = 1;
	/**
	 * length and width of the level, probably wont need these values here, this would be needed where I instantiate
	 * the Level object inside of a loop that loops over all the sectors of the level
	 */
	int height;
	int width;
	int sectorsX;
	int sectorsY;
	
	/**
	 * width and height of the wall objects, probably wont need this
	 */
	int w;
	int h;
	/**
	 * the maximum variation for the tunnel wall, so if the wall is 100 high and variation is set to 10, the
	 * wall can be anything from 90 to 110 pixels, this applies to floor and ceiling
	 */
	int variation = 200;
	int deltaLength;
	/**
	 * average tunnel width value, when the variation is 0 on both the ceiling and the floor the tunnel will be
	 * this wide
	 */
	int avgTunnelWidth;
	/**
	 * boolean state variable for making gaps in the floor and ceiling, rarely false (not a lot of gaps)
	 */
	boolean exists = true;
	
	/**
	 * Array full of colors for screen sector testing
	 */
	Color[] colors = {Color.blue, Color.green, Color.red, Color.yellow, Color.gray, Color.black, Color.white};
	
	public void init() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		height = mode.getHeight();
		width = mode.getWidth();
		sectorsX = (width * difficulty) / (16 * difficulty);
		sectorsY = height / 3;
	}

	public LevelRect(double x, double y, int w, int h) {
		super(x, y, w, h);
		this.init();
		Random rand = new Random((long)((x + y)));
		exists = (rand.nextInt(20 - 1) + 1) != 1;
		if (!exists) return;
	}
	
	public void doesntExist()
	{
		exists = false;
	}
	
	public boolean collision(Rect rect) {
		Point screenSectors = new Point(0, 0);

		
		for (screenSectors.x = 0; screenSectors.x < sectorsX; screenSectors.x++) {
			for (screenSectors.y = 0; screenSectors.y < sectorsY; screenSectors.y++) {
				if (screenSectors.y != 1) {
					if (rect.overlaps(new Rect((double)screenSectors.x, (double)screenSectors.y, 2, 2)))
						return true;
				}
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {
		Point screenSectors = new Point(0, 0);


		for (screenSectors.x = 0; screenSectors.x < sectorsX; screenSectors.x++)
		{
			for (screenSectors.y = 0; screenSectors.y < sectorsY; screenSectors.y++)
			{
				Level l = new Level(screenSectors.x, screenSectors.y);
				if (screenSectors.y == 1) l.doesntExist();
				if (l.exists)
				{
					if (screenSectors.y < 1)
					{
						g.setColor(Color.black);
						g.fillRect(screenSectors.x * sectorsX, (screenSectors.y * sectorsY) - l.deltaLength, sectorsX, sectorsY);
					}
					if (screenSectors.y > 1)
					{
						g.setColor(Color.black);
						g.fillRect(screenSectors.x * sectorsX, (screenSectors.y * sectorsY) + l.deltaLength, sectorsX, sectorsY);
					}

				}
				else
				{
					g.setColor(Color.blue);
					g.fillRect(screenSectors.x * sectorsX, screenSectors.y * sectorsY, sectorsX, sectorsY);
				}



				/*
				// TEST X SECTORS
				for (screenSectors.x = 0; screenSectors.x < sectorsX; screenSectors.x++)
				{
					g.setColor(colors[(int)screenSectors.x % 7]);
					g.fillRect(screenSectors.x * sectorsX, screenSectors.y * sectorsY, sectorsX, sectorsY);
				}

				// TEST Y SECTORS

				for (screenSectors.y = 0; screenSectors.y < sectorsY; screenSectors.y++)
				{
					g.setColor(colors[(int)screenSectors.y % 7]);
					g.fillRect(screenSectors.x * sectorsX, screenSectors.y * sectorsY, sectorsX, sectorsY);
				}
				 */
			}
		}
	}

}
